package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator.CalculatorPresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputPresenterFactory
import uk.co.sentinelweb.bitwatcher.common.mapper.CurrencyPairMapper
import uk.co.sentinelweb.bitwatcher.common.rx.BwSchedulers
import uk.co.sentinelweb.domain.*
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType
import uk.co.sentinelweb.use_case.AccountsRepositoryUseCase
import uk.co.sentinelweb.use_case.MarketsDataUseCase
import uk.co.sentinelweb.use_case.TickerUseCase
import uk.co.sentinelweb.use_case.TradeUseCase
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject


class TradePresenter @Inject constructor(
        private val view: TradeContract.View,
        private val accountsRepositoryUseCase: AccountsRepositoryUseCase,
        private val marketsDataUseCase: MarketsDataUseCase,
        private val tickerUseCase: TickerUseCase,
        private val tradeUseCase: TradeUseCase,
        private val state: TradeState,
        private val schedulers: BwSchedulers,
        private val displayMapper:TradeDisplayMapper,
        private val marketMapper: CurrencyPairMapper,
        inputPresenterFactory: TradeInputPresenterFactory
) : TradeContract.Presenter, TradeInputContract.Interactions {
    companion object {

        val TAG = TradePresenter::class.java.simpleName
    }

    private val subscriptions = CompositeDisposable()

    private val buyInputPresenter:TradeInputContract.Presenter

    private val sellInputPresenter:TradeInputContract.Presenter

    init {
        view.setPresenter(this)
        buyInputPresenter  = view.getInputPresenter(inputPresenterFactory, this, TradeType.BID)
        sellInputPresenter = view.getInputPresenter(inputPresenterFactory, this, TradeType.ASK)
        view.setData(displayMapper.mapDisplay(state))
    }

    override fun onEnter() {
        if (subscriptions.size() == 0) {
            init()
        }
    }

    override fun onExit() {
    }

    override fun onCreate() {
        if (subscriptions.size() == 0) {
            init()
        }
    }

    private fun init() {
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
        if (subscriptions.size() == 0) {
            init()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        subscriptions.clear()
    }

    override fun onExecutePressed(amount: AmountDomain, price: BigDecimal, type: TradeType) {
        state.account?.let { acct ->
            val trade = TransactionItemDomain.TradeDomain(
                    "",
                    Date(),
                    amount.amount,
                    amount.currencyCode,
                    type,
                    price,
                    state.market.base,
                    BigDecimal.ZERO,
                    CurrencyCode.NONE,
                    TransactionItemDomain.TradeDomain.TradeStatus.INITIAL
            )
            val disposable = TradeDisposable()
            tradeUseCase.placeTrade(acct, trade)
                    .observeOn(schedulers.main)
                    .subscribeOn(schedulers.network) // TODO this should be mode to orchestrator case as it might be different for various account types
                    .subscribe(disposable)
            subscriptions.add(disposable)
        }
    }

    override fun onAccountButtonClick() {
        state.accountNames?.let { names -> view.showAccountSeletor(names) }
    }

    override fun onAccountSelected(index: Int) {
        state.accounts?.get(index)?.let { acct ->
            state.account = acct
            val marketListDisposable = MarketListDisposable()
            marketsDataUseCase.getMarkets(acct)
                    .observeOn(schedulers.main)
                    .subscribeOn(schedulers.disk)
                    .subscribe(marketListDisposable)
            subscriptions.add(marketListDisposable)
        }
    }

    override fun onMarketButtonClick() {
        val marketNames = mutableListOf<String>()
        state.markets?.forEach({ market -> marketNames.add(marketMapper.mapName(market)) })
        view.showMarketsSelector(marketNames.toTypedArray())
    }

    override fun onMarketSelected(index: Int) {
        state.market = state.markets?.get(index) ?: CurrencyPair.NONE
        buyInputPresenter.setMarketAndAccount(state.account, state.market)
        sellInputPresenter.setMarketAndAccount(state.account, state.market)
        loadRate()
        view.setData(displayMapper.mapDisplay(state))
    }

    override fun onTabClicked(isBuy: Boolean) {
        view.showTabContent(isBuy)
    }

    private fun loadRate() {
        if (state.market != CurrencyPair.NONE) {
            val priceSubscriber = PriceDisposable()
            tickerUseCase.getRate(state.market.currency, state.market.base)
                    .subscribeOn(schedulers.database)
                    .observeOn(schedulers.main)
                    .subscribe(priceSubscriber)
            subscriptions.add(priceSubscriber)
        }
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
            view.setData(displayMapper.mapDisplay(state))
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "Error getting markets for account: ${state.account?.name}", e)
        }
    }

    inner class PriceDisposable : DisposableSingleObserver<BigDecimal>() {

        override fun onSuccess(rate: BigDecimal) {
            state.currentPrice = rate
            buyInputPresenter.setCurrentPrice(state.currentPrice)
            sellInputPresenter.setCurrentPrice(state.currentPrice)
            view.setData(displayMapper.mapDisplay(state))
        }

        override fun onError(exception: Throwable) {
            Log.d(CalculatorPresenter.TAG, "Error gettting ticker", exception)
        }

    }

    inner class TradeDisposable : DisposableSingleObserver<TransactionItemDomain.TradeDomain>() {

        override fun onSuccess(trade: TransactionItemDomain.TradeDomain) {

        }

        override fun onError(exception: Throwable) {
            Log.d(CalculatorPresenter.TAG, "Error placing trade", exception)
        }

    }
}