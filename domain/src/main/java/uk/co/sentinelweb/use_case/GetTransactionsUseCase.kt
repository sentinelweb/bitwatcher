package uk.co.sentinelweb.use_case

import io.reactivex.Observable
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain


interface GetTransactionsUseCase {
    enum class Type {TRANSACTION_ONLY, TRADES_ONLY}
    fun getTransactionsForAccount(account: AccountDomain, type:Type?):Observable<List<TransactionItemDomain>>
    fun getTransactions(type:Type? = null):Observable<List<TransactionItemDomain>>
}