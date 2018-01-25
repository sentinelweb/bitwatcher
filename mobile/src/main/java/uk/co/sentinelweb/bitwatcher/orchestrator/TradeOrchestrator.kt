package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Observable
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TradeDatabaseInteractor
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.bitwatcher.net.TradeDataInteractor
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.TransactionItemDomain
import uk.co.sentinelweb.use_case.TradeUseCase
import javax.inject.Inject
import javax.inject.Named

class TradeOrchestrator @Inject constructor(
        private @Named(NetModule.BITSTAMP) val bsTradesInteractor: TradeDataInteractor,
        private @Named(NetModule.BINANCE) val bnTradesInteractor: TradeDataInteractor,
        private val tradeInteractor:TradeDatabaseInteractor
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

}