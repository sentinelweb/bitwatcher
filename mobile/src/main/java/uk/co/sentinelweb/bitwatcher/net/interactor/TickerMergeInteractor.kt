package uk.co.sentinelweb.bitwatcher.net.interactor

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode.*
import uk.co.sentinelweb.bitwatcher.domain.CurrencyPair
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.bitwatcher.net.TickerDataInteractor
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
import javax.inject.Named

class TickerMergeInteractor @Inject constructor(
        private @Named(NetModule.BITSTAMP) val tickerBitstampInteractor: TickerDataInteractor,
        private @Named(NetModule.COINFLOOR) val tickerCoinfloorInteractor: TickerDataInteractor,
        private @Named(NetModule.KRAKEN) val tickerKrakenInteractor: Observable<TickerDataInteractor>,
        private @Named(NetModule.BINANCE) val tickerBinanceInteractor: TickerDataInteractor
) {
    companion object {
        val TAG = TickerMergeInteractor::class.java.simpleName
    }

    val priceCache = mutableMapOf<String, BigDecimal>()
    fun getMergedTickers(): Observable<TickerDomain> {
        return Observable.mergeDelayError(
                tickerBitstampInteractor.getTickers(listOf(BTC, ETH, XRP), listOf(USD, EUR)),
                tickerCoinfloorInteractor.getTickers(listOf(BTC, BCH), listOf(GBP)),
                tickerBinanceInteractor.getTicker(IOTA, BTC)
                        .flatMap { tickerDomain -> convertTickers(tickerDomain) },
                Observable.mergeDelayError(
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, EUR) },
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, USD) },
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(ETH, GBP) }
                )
        ).doOnNext({ td -> priceCache.put(CurrencyPair.getKey(td.currencyCode, td.baseCurrencyCode), td.last.setScale(6, RoundingMode.HALF_EVEN)) })
                .onErrorResumeNext({ _: Throwable -> Observable.empty() })
    }

    private fun convertTickers(tickerDomain: TickerDomain): ObservableSource<out TickerDomain> {
        var list: MutableList<Observable<TickerDomain>> = mutableListOf()
        listOf<CurrencyCode>(USD, GBP, EUR).forEach { code ->
            val single = Single.fromCallable({
                val btcRate = priceCache.get(CurrencyPair.getKey(BTC, code))
                val convertedPrice = btcRate?.multiply(tickerDomain.last) ?: BigDecimal.ZERO
                TickerDomain(tickerDomain.name, tickerDomain.from, convertedPrice, tickerDomain.currencyCode, code)
            }).doOnError { e -> Log.d(TAG, "Error making ticker ${tickerDomain.currencyCode} ${code}", e) }
            list.add(single.toObservable())
        }
        return Observable.mergeDelayError(list)
    }


}