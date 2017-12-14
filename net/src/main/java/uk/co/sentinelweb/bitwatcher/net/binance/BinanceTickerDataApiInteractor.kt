package uk.co.sentinelweb.bitwatcher.net.binance

import io.reactivex.Observable
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.binance.BinanceExchange
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h
import org.knowm.xchange.binance.service.BinanceMarketDataServiceRaw
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.CurrencyPair
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import uk.co.sentinelweb.bitwatcher.net.TickerDataInteractor
import java.util.*
import java.util.concurrent.Callable


class BinanceTickerDataApiInteractor(
        private val mapper: TickerMapper = TickerMapper()
) : TickerDataInteractor {

    override fun getTickers(currencyCodes: List<CurrencyCode>, baseCurrencyCodes: List<CurrencyCode>): Observable<TickerDomain> {
        val observables: MutableList<Observable<TickerDomain>> = mutableListOf<Observable<TickerDomain>>()
        for (base in baseCurrencyCodes) {
            for (code in currencyCodes) {
                observables.add(getTicker(code,base))
            }
        }
        return Observable.mergeDelayError(observables)
    }

    override fun getTicker(currencyCode:CurrencyCode, baseCurrencyCode:CurrencyCode): Observable<TickerDomain> {
        return Observable.fromCallable(object : Callable<TickerDomain> {
            override fun call(): TickerDomain {
                val binanceMarketDataServiceRaw = ExchangeFactory.INSTANCE
                        .createExchange(BinanceExchange::class.java.name).marketDataService
                        as BinanceMarketDataServiceRaw
                val ticker = binanceMarketDataServiceRaw.ticker24h(CurrencyPair.getKey(currencyCode, baseCurrencyCode))
                return mapper.map(ticker, currencyCode, baseCurrencyCode)
            }
        })
    }

    class TickerMapper {
        fun map(ticker: BinanceTicker24h, currency:CurrencyCode, base:CurrencyCode): TickerDomain {
            return TickerDomain(
                    TickerDomain.BASIC,
                    Date(),
                    ticker.lastPrice,
                    currency,
                    base)
        }
    }
}