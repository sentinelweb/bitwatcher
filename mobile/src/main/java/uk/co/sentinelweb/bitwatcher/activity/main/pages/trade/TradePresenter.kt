package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputPresenterFactory
import uk.co.sentinelweb.bitwatcher.common.extensions.div
import uk.co.sentinelweb.bitwatcher.common.mapper.AmountFormatter
import uk.co.sentinelweb.bitwatcher.common.mapper.CurrencyPairMapper
import uk.co.sentinelweb.bitwatcher.common.mapper.StringMapper
import uk.co.sentinelweb.bitwatcher.common.rx.BwSchedulers
import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.TransactionItemModel
import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.TransactionListContract
import uk.co.sentinelweb.bitwatcher.common.wrap.LogWrapper
import uk.co.sentinelweb.domain.*
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeStatus.PLACED
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
        private val displayMapper: TradeDisplayMapper,
        private val marketMapper: CurrencyPairMapper,
        private val strings: StringMapper,
        private val amountFormatter: AmountFormatter,
        private val log: LogWrapper = LogWrapper(),
        inputPresenterFactory: TradeInputPresenterFactory
) :
        TradeContract.Presenter,
        TradeInputContract.Interactions,
        TransactionListContract.Interactions {
    companion object {
        val TAG = TradePresenter::class.java.simpleName
    }

    private val subscriptions = CompositeDisposable()
    private val buyInputPresenter: TradeInputContract.Presenter
    private val sellInputPresenter: TradeInputContract.Presenter
    private val listPresenter: TransactionListContract.Presenter

    init {
        log.tag(this)
        listPresenter = view.getListPresenter()
        listPresenter.setInteractions(this)
        view.setPresenter(this)
        buyInputPresenter = view.getInputPresenter(inputPresenterFactory, this, TradeType.BID)
        sellInputPresenter = view.getInputPresenter(inputPresenterFactory, this, TradeType.ASK)
        updateView()
        view.showTabContent(TradeContract.View.Tab.OPEN_TRADES)
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
            var tradeAmount = amount
            if ( amount.currencyCode == state.market.base) {
                tradeAmount = AmountDomain(amount.amount.div(price), state.market.currency)
            }

            val trade = TransactionItemDomain.TradeDomain(
                    System.currentTimeMillis().toString(),
                    Date(),
                    tradeAmount.amount,
                    state.market.currency,
                    type,
                    price,
                    state.market.base,
                    BigDecimal.ZERO,
                    CurrencyCode.NONE,
                    PLACED
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

            refreshTrades(acct)
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
        updateView()
    }

    override fun onSelectionChanged(selection: Set<TransactionItemModel>) {
        state.selectedTrades = selection
        updateView()
    }

    override fun onTabClicked(tab: TradeContract.View.Tab) {
        view.showTabContent(tab)
    }

    private fun refreshTrades(acct: AccountDomain): Boolean {
        val openTradesListDisposable = OpenTradesDisposable()
        tradeUseCase.getOpenTrades(acct)
                .observeOn(schedulers.main)
                .subscribeOn(schedulers.network)
                .map { openTradeList -> mapTradeList(openTradeList, acct) }
                .subscribe(openTradesListDisposable)
        return subscriptions.add(openTradesListDisposable)
    }


    private fun mapTradeList(trades: List<TransactionItemDomain.TradeDomain>, acct: AccountDomain): List<TransactionItemModel> {
        val modelList = mutableListOf<TransactionItemModel>()
        trades.forEach { trade -> modelList.add(TransactionItemModel(trade, acct)) }
        return modelList
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

    override fun onDeleteTradesClick() {
//        val selectedTrades = mutableMapOf<AccountDomain, MutableSet<TransactionItemDomain.TradeDomain>>()
//        state.selectedTrades?.forEach { item ->
//            val value = selectedTrades.get(item.account) ?: mutableSetOf()
//            value.add(item.domain as TransactionItemDomain.TradeDomain)
//            selectedTrades.put(item.account, value)
//        }

        val cancelTradesDisposable = CancelTradesDisposable()
        tradeUseCase.cancelOpenTrades(state.account!!, state.selectedTrades?.flatMap { model -> listOf(model.domain as TransactionItemDomain.TradeDomain) }?.toSet()!!)
                .subscribeOn(schedulers.database)
                .observeOn(schedulers.main)
                .subscribe(cancelTradesDisposable)
        subscriptions.add(cancelTradesDisposable)
    }

    private fun updateView() {
        view.setData(displayMapper.mapDisplay(state))
    }

    inner class AccountListDisposable : DisposableSingleObserver<List<AccountDomain>>() {

        override fun onSuccess(allAccounts: List<AccountDomain>) {
            state.accounts = allAccounts.filter { acc -> acc.type == AccountType.GHOST }
            val accountNames = mutableListOf<String>()
            state.accounts?.forEach { acc ->
                accountNames.add(acc.name)
            }
            state.accountNames = accountNames.toTypedArray()
            subscriptions.remove(this)
        }

        override fun onError(e: Throwable) {
            log.d("Error getting accounts", e)
        }

    }

    inner class MarketListDisposable : DisposableMaybeObserver<List<CurrencyPair>>() {

        override fun onComplete() {}

        override fun onSuccess(markets: List<CurrencyPair>) {
            state.markets = markets
            updateView()
            subscriptions.remove(this)
        }

        override fun onError(e: Throwable) {
            log.d("Error getting markets for account: ${state.account?.name}", e)
        }

    }

    inner class PriceDisposable : DisposableSingleObserver<BigDecimal>() {

        override fun onSuccess(rate: BigDecimal) {
            state.currentPrice = rate
            buyInputPresenter.setCurrentPrice(state.currentPrice)
            sellInputPresenter.setCurrentPrice(state.currentPrice)
            updateView()
            subscriptions.remove(this)
        }

        override fun onError(exception: Throwable) {
            log.d("Error getting price", exception)
        }

    }

    inner class TradeDisposable : DisposableSingleObserver<TransactionItemDomain.TradeDomain>() {

        override fun onSuccess(trade: TransactionItemDomain.TradeDomain) {
            state.account?.let { a -> refreshTrades(a) }
            if (trade.status == PLACED) {
                view.showMessage(strings.getString(R.string.message_order_placed, trade.type.toString(), amountFormatter.format(trade.amount, trade.currencyCodeFrom)))
                (if (trade.type == TradeType.BID) buyInputPresenter else sellInputPresenter).clearTrade()
            }
            subscriptions.remove(this)
        }

        override fun onError(exception: Throwable) {
            log.d("Error placing trade", exception)
        }

    }

    inner class OpenTradesDisposable : DisposableObserver<List<TransactionItemModel>>() {

        override fun onNext(list: List<TransactionItemModel>) {
            listPresenter.bindData(list)
        }

        override fun onComplete() {
            subscriptions.remove(this)
        }


        override fun onError(exception: Throwable) {
            log.d("Error getting open trades", exception)
        }

    }

    inner class CancelTradesDisposable : DisposableSingleObserver<Boolean>() {

        override fun onSuccess(result: Boolean) {
            if (result) {
                view.showMessage("Trades deleted")
            } else {
                view.showMessage("Error deleting trades", true)
            }
            state.selectedTrades = null
            state.account?.let { acct -> refreshTrades(acct) }
            updateView()
            subscriptions.remove(this)
        }

        override fun onError(exception: Throwable) {
            log.d("Error deleteing trades", exception)
        }

    }
}