package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.domain.TransactionItemDomain

data class TransactionsState(
        var transactionList:MutableList<TransactionItemDomain> = mutableListOf()) {
    data class TransactionsModel(val transactionList:List<TransactionItemDomain>) {

    }
}