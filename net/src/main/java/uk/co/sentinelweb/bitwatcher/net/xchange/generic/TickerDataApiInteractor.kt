package uk.co.sentinelweb.bitwatcher.net.xchange.generic

import io.reactivex.Observable
import org.knowm.xchange.dto.marketdata.Ticker
import uk.co.sentinelweb.bitwatcher.net.TickerDataInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.CurrencyPairLookup
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain
import java.util.*
import java.util.concurrent.Callable


class TickerDataApiInteractor(
        private val service: ExchangeService,
        private val mapper: TickerMapper = TickerMapper()
): TickerDataInteractor {

    override fun getTicker(currencyCode: CurrencyCode, baseCurrencyCode: CurrencyCode): Observable<TickerDomain> {
        return Observable.fromCallable(object : Callable<TickerDomain> {
            override fun call(): TickerDomain {
                val lookup = CurrencyPairLookup.lookup(currencyCode, baseCurrencyCode)
                val ticker = service.marketDataService.getTicker(lookup)
                return mapper.map(ticker)
            }
        })
    }

    override fun getTickers(currencyCodes: List<CurrencyCode>, baseCurrencyCodes: List<CurrencyCode>): Observable<TickerDomain> {
        val observables: MutableList<Observable<TickerDomain>> = mutableListOf<Observable<TickerDomain>>()
        for (base in baseCurrencyCodes) {
            for (code in currencyCodes) {
                observables.add(getTicker(code,base))
            }
        }
        return Observable.mergeDelayError(observables)
    }

    class TickerMapper {
        fun map(ticker: Ticker): TickerDomain {
            return TickerDomain(
                    TickerDomain.NAME_CURRENT,
                    ticker.timestamp ?: Date(),
                    ticker.last,
                    CurrencyCode.lookup(ticker.currencyPair.base.currencyCode),
                    CurrencyCode.lookup(ticker.currencyPair.counter.currencyCode))
        }
    }
}