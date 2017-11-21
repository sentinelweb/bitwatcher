package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Observable
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.bitstamp.BitstampExchange
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.dto.marketdata.Ticker
import uk.co.sentinelweb.bitwatcher.domain.TickerData
import java.util.concurrent.Callable


class TickerDataApiInteractor(val mapper: TickerMapper = TickerMapper()) {

    fun getTicker(): Observable<TickerData> {
        return Observable.fromCallable(object : Callable<TickerData> {
            override fun call(): TickerData {
                val bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange::class.java.name)
                val marketDataService = bitstamp.marketDataService

                val ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
                return mapper.map(ticker)
            }
        })
    }

    class TickerMapper {
        fun map(ticker: Ticker): TickerData {
            return TickerData(
                    ticker.timestamp,
                    ticker.last,
                    ticker.currencyPair.base.currencyCode,
                    ticker.currencyPair.counter.currencyCode)
        }
    }
}