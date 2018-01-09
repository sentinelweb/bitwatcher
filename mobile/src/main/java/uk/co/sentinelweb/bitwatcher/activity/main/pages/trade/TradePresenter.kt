package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputPresenterFactory
import uk.co.sentinelweb.bitwatcher.common.rx.BwSchedulers
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.use_case.AccountsRepositoryUseCase
import uk.co.sentinelweb.use_case.MarketsDataUseCase
import javax.inject.Inject


class TradePresenter @Inject constructor(
        private val view: TradeContract.View,
        private val accountsRepositoryUseCase: AccountsRepositoryUseCase,
        private val marketsDataUseCase: MarketsDataUseCase,
        private val state: TradeState = TradeState(),
        private val schedulers: BwSchedulers,

        inputPresenterFactory: TradeInputPresenterFactory
) : TradeContract.Presenter {
    companion object {
        val TAG = TradePresenter::class.java.simpleName
    }

    private val subscriptions = CompositeDisposable()
    private val buyInputPresenter = view.getInputPresenter(inputPresenterFactory, true)
    private val sellInputPresenter = view.getInputPresenter(inputPresenterFactory, false)
    init {
        view.setViewPresenter(this)
    }

    override fun onEnter() {
    }

    override fun onExit() {
    }

    override fun onCreate() {
        val disposable = AccountListDisposable()
        accountsRepositoryUseCase.singleAllAccounts()
                .observeOn(schedulers.main)
                .subscribeOn(schedulers.database)
                .subscribe(disposable)
        subscriptions.add(disposable)
    }

    override fun onDestroy() {

    }

    override fun view(): View {
        return view as View
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        subscriptions.clear()
    }

    override fun onAccountButtonClick() {
        state.accountNames?.let { names -> view.showAccountSeletor(names) }
    }

    override fun onAccountSelected(index: Int) {
        val acct = state.accounts?.get(index)
        state.account = acct
        if (acct != null) {
            val marketListDisposable = MarketListDisposable()
            marketsDataUseCase.getMarkets(acct)
                    .observeOn(schedulers.main)
                    .subscribeOn(schedulers.database)
                    .subscribe(marketListDisposable)
        }
    }

    override fun onMarketButtonClick() {
        val marketNames = mutableListOf<String>()
        state.markets?.forEach({ market -> marketNames.add("${market.currency}/${market.base}") })
        view.showMarketsSelector(marketNames.toTypedArray())
    }

    override fun onMarketSelected(index: Int) {
        state.market = state.markets?.get(index) ?: CurrencyPair.NONE
        buyInputPresenter.setMarketAndAccount(state.account, state.market)
    }

    override fun onTabClicked(isBuy: Boolean) {
        view.showTabContent(isBuy)
    }

    inner class AccountListDisposable : DisposableSingleObserver<List<AccountDomain>>() {
        override fun onSuccess(allAccounts: List<AccountDomain>) {
            state.accounts = allAccounts.filter { acc -> acc.type == AccountType.GHOST }
            val accountNames = mutableListOf<String>()
            state.accounts?.forEach { acc ->
                accountNames.add(acc.name)
            }
            state.accountNames = accountNames.toTypedArray()
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "Error getting accounts", e)
        }
    }

    inner class MarketListDisposable : DisposableMaybeObserver<List<CurrencyPair>>() {
        override fun onComplete() {}

        override fun onSuccess(markets: List<CurrencyPair>) {
            state.markets = markets
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "Error getting markets for account: ${state.account?.name}", e)
        }
    }

}