package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Observable
import io.reactivex.Single
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.domain.TradeDomain


interface TradeDataInteractor {
    fun getUserTradesForPair(pair:CurrencyPair): Single<List<TradeDomain>>
    fun getUserTradesForPairs(currencyPairs: List<CurrencyPair>): Observable<List<TradeDomain>>
}