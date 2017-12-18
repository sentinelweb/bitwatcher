package uk.co.sentinelweb.bitwatcher.net.interactor

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.bitwatcher.net.TickerDataInteractor
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.CurrencyCode.*
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.domain.TickerDomain
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import java.util.concurrent.Callable
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
                Observable.mergeDelayError(
                        tickerCoinfloorInteractor.getTickers(listOf(BCH), listOf(GBP)),
                        tickerCoinfloorInteractor.getTickers(listOf(BTC), listOf(GBP))
                                .flatMap { btcGbpTicker -> convertXrpGbpTicker(btcGbpTicker) }
                ),
                tickerBinanceInteractor.getTicker(IOTA, BTC)
                        .flatMap { tickerDomain -> convertTickers(tickerDomain) },
                Observable.mergeDelayError(
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, EUR) },
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, USD) },
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(ETH, GBP) }
                ))
                .onErrorResumeNext({ _: Throwable -> Observable.empty() })
                .doOnNext({ td -> priceCache.put(CurrencyPair.getKey(td.currencyCode, td.baseCurrencyCode), td.last) })

    }

    private fun convertTickers(tickerDomain: TickerDomain): ObservableSource<out TickerDomain> {
        val list: MutableList<Observable<TickerDomain>> = mutableListOf()
        listOf<CurrencyCode>(USD, GBP, EUR).forEach { code ->
            val single = Observable.fromCallable({
                val btcRate = priceCache.get(CurrencyPair.getKey(BTC, code))
                val convertedPrice = btcRate?.multiply(tickerDomain.last) ?: BigDecimal.ZERO
                TickerDomain(tickerDomain.name, tickerDomain.from, convertedPrice, tickerDomain.currencyCode, code)
            }).doOnError { e -> Log.d(TAG, "Error making ticker ${tickerDomain.currencyCode} ${code}", e) }
            list.add(single)
        }
        return Observable.mergeDelayError(list)
    }

    private fun convertXrpGbpTicker(btcGbpTicker: TickerDomain): ObservableSource<out TickerDomain> {
        return Observable.merge(
                Observable.just(btcGbpTicker),
                Observable.fromCallable(object : Callable<TickerDomain> {
                    override fun call(): TickerDomain {
                        val btcUsdRate = priceCache.get(CurrencyPair.getKey(BTC, USD))
                        val xrpUsdRate = priceCache.get(CurrencyPair.getKey(XRP, USD))
                        val usdGbpRate = btcGbpTicker.last.divide(btcUsdRate, 8, RoundingMode.HALF_EVEN)
                        val convertedPrice = xrpUsdRate?.multiply(usdGbpRate) ?: BigDecimal.ZERO
                        return TickerDomain(TickerDomain.BASIC, Date(), convertedPrice, XRP, GBP)
                    }
                }).doOnError { e -> Log.d(TAG, "Error convert XRP->GBP ticker ", e) }
        )
    }

}