package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Observable
import io.reactivex.Single
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain

interface TransactionsDataInteractor {
    fun getTransactions(): Single<List<TransactionDomain>>
    fun getTransactionsForCurrencies(currencies: List<CurrencyCode>): Observable<List<TransactionDomain>>
}