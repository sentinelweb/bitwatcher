package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Observable
import org.knowm.xchange.dto.marketdata.Ticker
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.TickerData
import java.util.concurrent.Callable


class TickerDataApiInteractor(val service:BitstampService,val mapper: TickerMapper = TickerMapper()) {

    fun getTicker(currencyCode: CurrencyCode, baseCurrencyCode: CurrencyCode): Observable<TickerData> {
        return Observable.fromCallable(object : Callable<TickerData> {
            override fun call(): TickerData {
                val lookup = CurrencyPairLookup.lookup(currencyCode, baseCurrencyCode)!!
                val ticker = service.marketDataService.getTicker(lookup);
                return mapper.map(ticker)
            }
        })
    }

    fun getTickers(currencyCodes: List<CurrencyCode>, baseCurrencyCodes: List<CurrencyCode>): Observable<TickerData> {
        val observables: MutableList<Observable<TickerData>> = mutableListOf<Observable<TickerData>>()
        for (base in baseCurrencyCodes) {
            for (code in currencyCodes) {
                observables.add(getTicker(code,base))
            }
        }
        return Observable.merge(observables)
    }

    class TickerMapper {
        fun map(ticker: Ticker): TickerData {
            return TickerData(
                    ticker.timestamp,
                    ticker.last,
                    CurrencyCode.lookup(ticker.currencyPair.base.currencyCode)!!,
                    CurrencyCode.lookup(ticker.currencyPair.counter.currencyCode)!!)
        }
    }
}