package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherMemoryDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.domain.AccountType
import uk.co.sentinelweb.bitwatcher.domain.AccoutDomain
import javax.inject.Inject


class EditAccountFragmentPresenter @Inject constructor(
        val view: EditAccountContract.View,
        val dbMem: BitwatcherMemoryDatabase,
        val accountDomainMapper: AccountEntityToDomainMapper
) : EditAccountContract.Presenter {
    companion object {
        val TAG = EditAccountFragmentPresenter::class.java.simpleName
    }

    lateinit var state: EditAccountState

    val subscription = CompositeDisposable()

    override fun initialise(id: Long?) {
        state = EditAccountState(id)
        if (id != null) {
            subscription.add(dbMem.fullAccountDao()
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

    private fun mapToState(domain: AccoutDomain?) {
        state.domain = domain
        state.type = domain?.type
        state.name = domain?.name
        state.balances = domain?.balances?.toMutableList() ?: mutableListOf()
        view.updateState(state)
    }

    override fun onTypeChangeClick() {
        view.createAndShowTypeDialog()
    }

    override fun onTypeSelected(idx:Int) {
        state.type = AccountType.values()[idx]
        view.updateState(state)
    }


}
