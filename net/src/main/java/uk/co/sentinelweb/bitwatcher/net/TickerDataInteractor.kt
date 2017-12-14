package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Observable
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain

interface TickerDataInteractor {

    fun getTicker(currencyCode: CurrencyCode, baseCurrencyCode: CurrencyCode): Observable<TickerDomain>
    fun getTickers(currencyCodes: List<CurrencyCode>, baseCurrencyCodes: List<CurrencyCode>): Observable<TickerDomain>
}