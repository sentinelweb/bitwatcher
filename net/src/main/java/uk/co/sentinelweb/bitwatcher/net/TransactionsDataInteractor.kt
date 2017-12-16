package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Single
import uk.co.sentinelweb.domain.TransactionDomain

interface TransactionsDataInteractor {
    fun getTransactions(): Single<List<TransactionDomain>>
}