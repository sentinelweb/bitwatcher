package uk.co.sentinelweb.use_case

import io.reactivex.Observable
import uk.co.sentinelweb.domain.AccountDomain


interface GetTransactionsUseCase {
    enum class Type { TRANSACTION_ONLY, TRADES_ONLY }

    fun getTransactionsForAccount(account: AccountDomain, type: Type?): Observable<AccountDomain>
    fun getAllTransactionsByAccount(type: Type? = null): Observable<AccountDomain>
}