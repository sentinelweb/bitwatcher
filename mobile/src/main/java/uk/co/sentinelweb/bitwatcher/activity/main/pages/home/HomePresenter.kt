package uk.co.sentinelweb.bitwatcher.activity.main.pages.home

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row.AccountRowContract
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.bitwatcher.common.preference.BitwatcherPreferences
import uk.co.sentinelweb.domain.*
import uk.co.sentinelweb.domain.extensions.getPairKey
import uk.co.sentinelweb.domain.mappers.AccountTotalsMapper
import uk.co.sentinelweb.domain.mappers.CurrencyListGenerator
import uk.co.sentinelweb.use_case.AccountsRepositoryUseCase
import uk.co.sentinelweb.use_case.UpdateTickersUseCase
import java.math.BigDecimal.ZERO
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val view: HomeContract.View,
        private val state: HomeState,
        private val tickerModelMapper: TickerDisplayModelMapper,
        private val accountsRepositoryUseCase: AccountsRepositoryUseCase,
        private val preferences: BitwatcherPreferences,
        private val updateTickerUseCase: UpdateTickersUseCase

) : HomeContract.Presenter, AccountRowContract.Interactions {
    companion object {
        val TAG = HomePresenter::class.java.simpleName
    }

    private val subscriptions = CompositeDisposable()
    private val accountRowPresenters: MutableMap<Long, AccountRowContract.Presenter> = mutableMapOf()

    override fun init() {
        view.setPresenter(this)
        state.displayCurrency = preferences.getSelectedCurrency() ?: CurrencyCode.GBP
        view.setDisplayCurrency(state.displayCurrency.toString())
        state.displayRealItems = preferences.getViewRealItems()
        startTimerInterval()
        subscriptions.add(
                accountsRepositoryUseCase.flowAllAccounts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ list -> setAccounts(list) }))
        view.updateTotalsDisplay(state.totals)
        view.setDisplayRealAccounts(state.displayRealItems)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onStart() {
        init()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onStop() {
        cleanup()
    }

    override fun onEnter() {
    }

    override fun onExit() {
    }

    override fun cleanup() {
        subscriptions.dispose()
    }

    override fun view(): View {
        return view as View
    }

    override fun onCurrencyButtonClick() {
        view.showCurrencyDialog(CurrencyListGenerator.getCurrencyArray())

    }

    override fun onCurrencySelected(code: String) {
        state.displayCurrency = CurrencyCode.lookup(code)
        preferences.saveSelectedCurrency(state.displayCurrency)
        view.setDisplayCurrency(state.displayCurrency.toString())
        updateAccounts()
        updateTotals()
    }

    override fun onAddAccountClick() {
        view.launchEditAccountActivity(null)
    }

    // interactions
    override fun onChecked(account: AccountDomain, checked: Boolean) {
        if (checked) {
            state.selectedAccountIds.add(account.id!!)
        } else {
            state.selectedAccountIds.remove(account.id!!)
        }
        updateTotals()
    }

    override fun onEdit(account: AccountDomain) {
        view.launchEditAccountActivity(account.id)
    }

    override fun onDelete(account: AccountDomain) {
        subscriptions.add(accountsRepositoryUseCase
                .deleteAccount(account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            if (result) {
                                state.deletedAccount = account
                                view.showDeletedSnackBar()
                            }
                        },
                        { e -> Log.d(TAG, "error updating ticker data", e) }))
    }

    override fun restoreDeletedAccount() {
        if (state.deletedAccount != null) {
            // reset ids
            val balances = mutableListOf<BalanceDomain>()
            state.deletedAccount!!.balances.forEach { bal -> balances.add(bal.copy(id = null)) }
            val copy = state.deletedAccount!!.copy(id = null, balances = balances)

            subscriptions.add(accountsRepositoryUseCase
                    .saveAccount(copy)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                if (result) {
                                    state.deletedAccount = null
                                    view.showSnackBarMessage("Account restored")
                                }
                            },
                            { e -> Log.d(TAG, "error updating ticker data", e) }))
        }
    }

    override fun clearDeletedAccount() {
        state.deletedAccount = null
    }

    override fun onClick(account: AccountDomain) {
        view.launchAccountOverviewActivity(account.id!!)
    }

    override fun onDisplayRealAccountToggle() {
        state.displayRealItems = !state.displayRealItems
        preferences.saveViewRealItems(state.displayRealItems)
        view.setDisplayRealAccounts(state.displayRealItems)
        state.accounts.forEach { account ->
            val rowPresenter = accountRowPresenters.get(account.id!!)
            rowPresenter?.setVisible(account.type == AccountType.GHOST || state.displayRealItems)
        }
    }

    private fun setAccounts(list: List<AccountDomain>) {
        state.accounts = list
        view.clearAccountList()
        accountRowPresenters.clear()
        list.forEach { accountDomain ->
            if (!accountRowPresenters.containsKey(accountDomain.id)) {
                val presenter = view.addAccount(this)
                presenter.init()
                accountRowPresenters.put(accountDomain.id!!, presenter)
            }

            val rowPresenter = accountRowPresenters.get(accountDomain.id!!)
            rowPresenter?.bindData(accountDomain, state.displayCurrency, state.prices)
            rowPresenter?.setVisible(accountDomain.type == AccountType.GHOST || state.displayRealItems)
        }
        updateTotals()
    }

    private fun updateAccounts() {
        state.accounts.forEach { accountDomain ->
            accountRowPresenters
                    .get(accountDomain.id!!)
                    ?.bindData(accountDomain, state.displayCurrency, state.prices)

        }
    }

    private fun startTimerInterval() {
        Observable.interval(20, TimeUnit.SECONDS)
                .flatMap({ _ -> updateTickerUseCase.downloadTickerToRepository() })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(UpdateTickersSubscriber())

        updateTickerUseCase.observeTickersFromRepository()
                .doOnNext({ tickerDomain -> state.prices.put(tickerDomain.getPairKey(), tickerDomain) })
                .map { t -> tickerModelMapper.map(t, state.tickerDisplay) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(TickersFromRepositorySubscriber())
    }

    private inner class TickersFromRepositorySubscriber : Observer<HomeState.TickerDisplay> {
        private lateinit var disposable: Disposable
        override fun onSubscribe(d: Disposable) {
            disposable = d
            subscriptions.add(d)
        }

        override fun onNext(model: HomeState.TickerDisplay) {
            view.updateTickerDisplay(model)
            updateAccounts()
            updateTotals()
        }

        override fun onError(t: Throwable?) {
            Log.d(TAG, "error updating ticker display", t)
        }

        override fun onComplete() {
            subscriptions.remove(disposable)
        }
    }

    private inner class UpdateTickersSubscriber : Observer<TickerDomain> {
        private lateinit var disposable: Disposable
        override fun onSubscribe(d: Disposable) {
            disposable = d
            subscriptions.add(d)
        }

        override fun onNext(t: TickerDomain) {
            Log.d(TAG, "updated ticker data ${t.currencyCode}->${t.baseCurrencyCode} = ${t.last}")
        }

        override fun onError(t: Throwable?) {
            Log.d(TAG, "error updating tickers", t)
        }

        override fun onComplete() {
            subscriptions.remove(disposable)
        }
    }

    private fun updateTotals() {
        var ghostTotal = ZERO
        var realTotal = ZERO

        state.accounts.forEach { account ->
            if (state.selectedAccountIds.isEmpty() || state.selectedAccountIds.contains(account.id)) {
                val total = AccountTotalsMapper.getTotal(account, state.displayCurrency, state.prices)
                if (account.type == AccountType.GHOST) {
                    ghostTotal += total
                } else {
                    realTotal += total
                }
            }
        }

        state.totals.ghostTotalDisplay = ghostTotal.dp(2) + " " + state.displayCurrency
        state.totals.realTotalDisplay = realTotal.dp(2) + " " + state.displayCurrency

        view.updateTotalsDisplay(state.totals)
    }

}