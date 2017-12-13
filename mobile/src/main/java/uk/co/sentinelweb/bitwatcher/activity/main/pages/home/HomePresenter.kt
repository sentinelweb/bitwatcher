package uk.co.sentinelweb.bitwatcher.activity.main.pages.home

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row.AccountRowContract
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountSaveInteractor
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.bitwatcher.common.preference.BitwatcherPreferences
import uk.co.sentinelweb.bitwatcher.domain.AccountDomain
import uk.co.sentinelweb.bitwatcher.domain.AccountType
import uk.co.sentinelweb.bitwatcher.domain.BalanceDomain
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.extensions.getPairKey
import uk.co.sentinelweb.bitwatcher.domain.mappers.AccountTotalsMapper
import uk.co.sentinelweb.bitwatcher.domain.mappers.CurrencyListGenerator
import uk.co.sentinelweb.bitwatcher.orchestrator.TickerDataOrchestrator
import java.math.BigDecimal.ZERO
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val view: HomeContract.View,
        private val state: HomeState,
        private val tickerModelMapper: TickerStateMapper,
        private val tickerEntityMapper: TickerEntityToDomainMapper,
        private val db: BitwatcherDatabase,
        private val orchestrator: TickerDataOrchestrator,
        private val orchestratorAccount: AccountSaveInteractor,
        private val accountDomainMapper: AccountEntityToDomainMapper,
        private val preferences: BitwatcherPreferences

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
        subscriptions.add(db.fullAccountDao()
                .flowFullAccounts()
                .map { list -> accountDomainMapper.mapFullList(list) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list -> setAccounts(list) }))
        view.updateTotalsDisplay(state.totals)
        view.setDisplayRealAccounts(state.displayRealItems)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        init()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        cleanup()
    }

    override fun onEnter() {
    }

    override fun onExit() {
    }

    override fun cleanup() {
        subscriptions.clear()
    }

    override fun view(): View {
        return view as View
    }

    override fun onCurrencyButtonClick() {
        view.showCurrencyDialog(CurrencyListGenerator.getCurrencyList())

    }

    override fun onCurrencySelected(code: String) {
        state.displayCurrency = CurrencyCode.lookup(code)!!
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
        subscriptions.add(orchestratorAccount
                .delete(account)
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

            subscriptions.add(orchestratorAccount
                    .save(copy)
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
        subscriptions.add(Observable.interval(20, TimeUnit.SECONDS)
                .flatMap({ _ -> orchestrator.downloadTickerToDatabase() })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        { t -> Log.d(TAG, "updated ticker data ${t.currencyCode}->${t.baseCode} = ${t.amount}") },
                        { e -> Log.d(TAG, "error updating ticker data", e) }))

        subscriptions.add(orchestrator.flowTickers()
                .map({ tickerEntity -> tickerEntityMapper.map(tickerEntity) })
                .doOnNext({ tickerDomain -> state.prices.put(tickerDomain.getPairKey(), tickerDomain) })
                .map { t -> tickerModelMapper.map(t, state.tickerDisplay) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { state ->
                            view.updateTickerDisplay(state)
                            updateAccounts()
                            updateTotals()
                        },
                        { e -> Log.d(TAG, "error updating ticker display", e) }))
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


// GDAX test - make network call in constructor so need to be wrapped in observale
//        val tickers = Observable.fromCallable(  object : Callable<TickerDataApiInteractor> {
//            override fun call(): TickerDataApiInteractor {
//                return TickerDataApiInteractor(GdaxService.Companion.GUEST)
//            }
//        }).flatMap({interactor -> interactor.getTickers(
//                listOf(CurrencyCode.BTC, CurrencyCode.ETH, CurrencyCode.BCH),
//                listOf(CurrencyCode.USD, CurrencyCode.EUR, CurrencyCode.GBP))})


//        subscription
//                .add(Observable.interval(10, TimeUnit.SECONDS)
//                        .flatMap ({ l -> mergedTicker.getMergedTickers() })
//                        .map { t -> tickerModelMapper.map(t, state.tickerState) }
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({ state -> homeView.updateTickerDisplay(state) },
//                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))

//        subscription
//                .add(Observable.interval(10, TimeUnit.SECONDS)
//                        .flatMap ({ l -> fullAccountsObservable })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({list -> homeView.setAccounts(list)},
//                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))


//subscriptions.add(db.tickerDao()
//                .flowAllTickers()
//                .map({ entities -> tickerEntityMapper.map(entities)})
//                .doOnNext({domainList -> state.prices = domainList;})
//                .flatMap({ domainList -> Flowable.fromIterable(domainList)})
//                //.doOnNext { t-> Log.d(TAG,"dbf: got value for ${t.currencyCode}->${t.baseCurrencyCode} = ${t.last}") }
//                .map { t -> tickerModelMapper.map(t, state.tickerState) }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { state -> view.updateTickerDisplay(state) },
//                        { e -> Log.d(TAG, "error updating ticker data", e) }))

