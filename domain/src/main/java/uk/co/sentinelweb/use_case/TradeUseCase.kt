package uk.co.sentinelweb.use_case

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain

interface TradeUseCase {

    fun placeTrade(account: AccountDomain, trade: TransactionItemDomain.TradeDomain): Single<TransactionItemDomain.TradeDomain>

    fun getOpenTrades(account: AccountDomain): Observable<List<TransactionItemDomain.TradeDomain>>

    //fun cancelOpenTrades(selectedTrades: Map<AccountDomain, Set<TransactionItemDomain.TradeDomain>>): Single<TransactionItemDomain.TradeDomain>

    fun cancelOpenTrades(acct: AccountDomain, trades:Set<TransactionItemDomain.TradeDomain>): Single<Boolean>

    fun checkOpenTrades():Observable<TransactionItemDomain.TradeDomain>

    fun checkOpenTrades(acct: AccountDomain):Observable<TransactionItemDomain.TradeDomain>

    fun checkOpenTrade(acct: AccountDomain, trade:TransactionItemDomain.TradeDomain): Maybe<TransactionItemDomain.TradeDomain>
}