package uk.co.sentinelweb.bitwatcher.net.interactor

import io.reactivex.Observable
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode.*
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.bitwatcher.net.TickerDataApiInteractor
import javax.inject.Inject
import javax.inject.Named

class TickerMergeInteractor @Inject constructor(
        private @Named(NetModule.BITSTAMP) val tickerBitstampInteractor: TickerDataApiInteractor,
        private @Named(NetModule.COINFLOOR) val tickerCoinfloorInteractor: TickerDataApiInteractor,
        private @Named(NetModule.KRAKEN) val tickerKrakenInteractor: Observable<TickerDataApiInteractor>
        ) {

    fun getMergedTickers(): Observable<TickerDomain> {
        return Observable.mergeDelayError(
                tickerBitstampInteractor.getTickers(listOf(BTC, ETH), listOf(USD, EUR)),
                tickerCoinfloorInteractor.getTickers(listOf(BTC, BCH), listOf(GBP)), // TODO crashing issue here when connection timing out
                Observable.mergeDelayError(
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, EUR) },
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, USD) },
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(ETH, GBP) }
                ))
                .onErrorResumeNext({ _: Throwable -> null })
    }
}