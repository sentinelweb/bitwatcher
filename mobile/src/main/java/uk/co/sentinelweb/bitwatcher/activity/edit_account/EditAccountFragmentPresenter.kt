package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.edit_account.validators.AccountValidator
import uk.co.sentinelweb.bitwatcher.activity.edit_account.validators.NameValidator
import uk.co.sentinelweb.bitwatcher.activity.edit_account.view.BalanceItemContract
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError
import uk.co.sentinelweb.bitwatcher.domain.AccountDomain
import uk.co.sentinelweb.bitwatcher.domain.AccountType
import uk.co.sentinelweb.bitwatcher.domain.BalanceDomain
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.orchestrator.AccountSaveOrchestrator
import java.io.Serializable
import javax.inject.Inject


class EditAccountFragmentPresenter @Inject constructor(
        private val view: EditAccountContract.View,
        private val db: BitwatcherDatabase,
        private val accountDomainMapper: AccountEntityToDomainMapper,
        private val nameValidator: NameValidator,
        private val accountValidator: AccountValidator,
        private val saveOrchestrator: AccountSaveOrchestrator
) : EditAccountContract.Presenter, BalanceItemContract.Interactions, LifecycleObserver {
    companion object {
        val TAG = EditAccountFragmentPresenter::class.java.simpleName
    }

    private lateinit var state: EditAccountState
    private val balancePresenters: MutableList<BalanceItemContract.Presenter> = mutableListOf()

    val subscription = CompositeDisposable()

    override fun initialise(id: Long?) {
        state = EditAccountState(id)
        if (id != null) {
            subscription.add(db.fullAccountDao()
                    .singleFullAccount(id)
                    .map { entity -> accountDomainMapper.mapFull(entity) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { domain -> mapToState(domain) },
                            { error -> Log.d(TAG, "Could not load account ${id}", error) }
                    )
            )
        }
    }

    override fun restoreState(domain:AccountDomain) {
        state = EditAccountState(domain.id)
        mapToState(domain)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        subscription.clear()
    }

    private fun mapToState(domain: AccountDomain?) {
        state.domain = domain
        state.type = domain?.type
        state.name = domain?.name
        val balances = domain?.balances ?: listOf()
        view.updateState(state)
        balances.forEach({ balanceDomain ->
            val presenter = view.addBalanceView()
            presenter.setInteractions(this)
            presenter.init()
            presenter.bindData(balanceDomain)
            balancePresenters.add(presenter)
        })
    }

    override fun onTypeChangeClick() {
        view.createAndShowTypeDialog()
    }

    override fun onTypeSelected(idx: Int) {
        state.type = AccountType.values()[idx]
        view.updateState(state)
    }

    override fun validateCurrencyCode(code: CurrencyCode): ValidationError {
        var error = ValidationError.OK
        balancePresenters.forEach({ presenter ->
            if (presenter.getCurrencyCode() == code) {
                error = ValidationError("This code is already added", ValidationError.Type.VALIDATION)
            }
        })
        return error
    }

    override fun onDelete(presenter: BalanceItemContract.Presenter) {
        val index = balancePresenters.indexOf(presenter)
        view.removeBalanceView(index)
        balancePresenters.removeAt(index)
    }

    override fun getCurrencyList(): List<CurrencyCode> {
        val remainingCodes = mutableListOf<CurrencyCode>()
        CurrencyCode.values().forEach { code -> remainingCodes.add(code) }
        remainingCodes.remove(CurrencyCode.NONE)
        balancePresenters.forEach { presenter -> remainingCodes.remove(presenter.getCurrencyCode()) }
        return remainingCodes.toList()
    }

    override fun addBalance() {
        val presenter = view.addBalanceView()
        presenter.setInteractions(this)
        presenter.init()
        balancePresenters.add(presenter)
    }

    override fun saveAndFinish() {
        val account = accountDomain()
        val validation = accountValidator.validate(account)
        if (validation == ValidationError.OK) {
            saveOrchestrator
                    .save(account)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ success ->
                        if (success) view.close()
                        else view.showSnackBar(ValidationError("Save failed :(", ValidationError.Type.GENERIC))
                    },
                            { t2 -> Log.d(TAG, "Exception saving", t2) })


        } else {
            view.showSnackBar(validation)
        }
    }

    private fun accountDomain(): AccountDomain {
        val balances = mutableListOf<BalanceDomain>()
        balancePresenters.forEach { presenter -> balances.add(presenter.getBalance()) }
        val account = AccountDomain(state.id, state.name ?: "", state.type ?: AccountType.INITIAL, balances)
        return account
    }

    override fun getSaveState(): Serializable {
        return accountDomain()
    }


    override fun validateName(value: String) {
        val validation = nameValidator.validate(value)
        view.showError(R.id.name_edit, validation)
        state.name = value
    }

}