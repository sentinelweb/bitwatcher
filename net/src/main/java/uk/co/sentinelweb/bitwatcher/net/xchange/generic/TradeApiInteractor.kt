package uk.co.sentinelweb.bitwatcher.net.xchange.generic

import io.reactivex.Observable
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.net.TradeDataInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService
import uk.co.sentinelweb.bitwatcher.net.xchange.mapper.TradeMapper
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import java.util.concurrent.Callable

class TradeApiInteractor(
        private val service: ExchangeService,
        private val mapper: TradeMapper = TradeMapper(),
        private val paramsProvider: TradeHistoryParamsProvider
) : TradeDataInteractor {

    override fun getUserTrades(): Observable<List<TradeDomain>> {
        return Observable.fromCallable(object : Callable<List<TradeDomain>> {
            override fun call(): List<TradeDomain> {
                val trades = service.tradeService.getTradeHistory(paramsProvider.provide(null))
                System.err.println("got trades:${trades.userTrades.size}")
                return mapper.map(trades.userTrades.toList())
            }
        })
    }

    override fun getUserTradesForPairs(currencyPairs: List<CurrencyPair>): Observable<List<TradeDomain>> {
        val observables: MutableList<Observable<List<TradeDomain>>> = mutableListOf<Observable<List<TradeDomain>>>()
        currencyPairs.forEach({pair -> observables.add(getUserTradesForPair(pair).toObservable())})
        return Observable.mergeDelayError(observables)
    }

    override fun getUserTradesForPair(pair: CurrencyPair): Single<List<TradeDomain>> {
        return Single.fromCallable(object : Callable<List<TradeDomain>> {
            override fun call(): List<TradeDomain> {
                System.err.println("getting trades ...")
                val trades = service.tradeService.getTradeHistory(paramsProvider.provide(pair))
                System.err.println("got trades:${trades.userTrades.size}")
                return mapper.map(trades.userTrades.toList())
            }
        })
    }
}