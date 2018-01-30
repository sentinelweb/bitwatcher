package uk.co.sentinelweb.use_case

import io.reactivex.Maybe
import io.reactivex.Observable
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain

interface BalanceUpdateUseCase {
    fun getBalances(): Observable<Boolean>
    fun updateBalanceFromTrade(acct:AccountDomain, trade:TransactionItemDomain.TradeDomain): Maybe<Boolean>
}