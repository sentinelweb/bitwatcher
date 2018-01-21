package uk.co.sentinelweb.use_case

import io.reactivex.Observable
import io.reactivex.Single
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain

interface TradeUseCase {

    fun placeTrade(account: AccountDomain, trade: TransactionItemDomain.TradeDomain): Single<TransactionItemDomain.TradeDomain>

    fun getOpenTrades(account: AccountDomain): Observable<List<TransactionItemDomain.TradeDomain>>
}