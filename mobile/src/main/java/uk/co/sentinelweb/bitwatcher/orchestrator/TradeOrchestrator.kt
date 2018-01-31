package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TickerRateInteractor
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TradeDatabaseInteractor
import uk.co.sentinelweb.bitwatcher.common.extensions.div
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.bitwatcher.net.TradeDataInteractor
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.TransactionItemDomain
import uk.co.sentinelweb.use_case.TradeUseCase
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Named

class TradeOrchestrator @Inject constructor(
        private @Named(NetModule.BITSTAMP) val bsTradesInteractor: TradeDataInteractor,
        private @Named(NetModule.BINANCE) val bnTradesInteractor: TradeDataInteractor,
        private val tradeInteractor: TradeDatabaseInteractor,
        private val accountInteractor: AccountInteractor,
        private val tickerRateInteractor: TickerRateInteractor
) : TradeUseCase {
    override fun placeTrade(account: AccountDomain, trade: TransactionItemDomain.TradeDomain): Single<TransactionItemDomain.TradeDomain> {
        return when (account.type) {
            AccountType.GHOST -> {
                return tradeInteractor.singleInsertOrUpdate(account, trade)
            }
            else -> Single.just(trade)
        }
    }

    override fun getOpenTrades(account: AccountDomain): Observable<List<TransactionItemDomain.TradeDomain>> {
        return when (account.type) {
            AccountType.BITSTAMP -> {
                return bsTradesInteractor.getOpenUserTrades()
            }
            AccountType.BINANCE -> {
                // TODO take pairs from the balances
                //val currencyPairs = listOf(CurrencyPair(CurrencyCode.IOTA, CurrencyCode.BTC))
                // bnTradesInteractor.getUserTradesForPairs(currencyPairs)
                // TODO wait for xchange 4.3.2 to fix a bug
                //bnTransactionsInteractor.getTransactionsForCurrencies(listOf(CurrencyCode.BTC))
                //return bnTradesInteractor.getOpenUserTrades()
                return Observable.empty()
            }
            AccountType.GHOST -> {
                return tradeInteractor.singleOpenTradesForAccount(account).toObservable()
            }
            else -> Observable.empty()
        }
    }

    override fun cancelOpenTrades(acct: AccountDomain, trades: Set<TransactionItemDomain.TradeDomain>): Single<Boolean> {
        return when (acct.type) {
            AccountType.GHOST -> tradeInteractor.singleDeleteTrades(acct, trades)
            else -> Single.just(false)
        }
    }

    override fun checkOpenTrades(): Observable<TransactionItemDomain.TradeDomain> {
        return accountInteractor
                .singleAllAccounts()
                .flatMapObservable { accountList -> Observable.fromIterable(accountList) }
                .flatMap { account: AccountDomain -> checkOpenTrades(account) }
    }

    override fun checkOpenTrades(acct: AccountDomain): Observable<TransactionItemDomain.TradeDomain> {
        return tradeInteractor
                .singlePendingTradesForAccount(acct)
                .flatMapObservable { tradeList -> Observable.fromIterable(tradeList) }
                .flatMap { trade -> checkOpenTrade(acct, trade).toObservable() }
    }

    override fun checkOpenTrade(acct: AccountDomain, trade: TransactionItemDomain.TradeDomain): Maybe<TransactionItemDomain.TradeDomain> {
        return when (acct.type) {
            AccountType.GHOST -> {
                tickerRateInteractor
                        .getRate(trade.currencyCodeTo, trade.currencyCodeFrom)
                        .filter { rate -> (trade.price - rate).abs() < rate.div(BigDecimal(500)) } //maybe
                        .map { _ ->
                            trade.copy(status = TransactionItemDomain.TradeDomain.TradeStatus.COMPLETE)
                        }
                        //.doOnSuccess { tradeUdpated -> a } // TODO update balances
                        .flatMap { tradeUpdated -> tradeInteractor.singleInsertOrUpdate(acct, tradeUpdated).toMaybe() }


            }
            else -> Maybe.empty()
        }
    }


//    override fun cancelOpenTrades(selectedTrades: Map<AccountDomain, Set<TransactionItemDomain.TradeDomain>>): Single<Boolean> {
//        return Single.fromCallable(object : Callable<Boolean> {
//            override fun call(): Boolean {
//                selectedTrades.forEach { account: AccountDomain ->
//                    return cancelOpenTrades(account, selectedTrades.get(account))
//                }
//            }
//
//        })
//
//    }
}