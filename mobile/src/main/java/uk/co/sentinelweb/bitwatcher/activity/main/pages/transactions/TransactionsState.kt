package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.domain.TransactionItem

data class TransactionsState(var transactionList:List<TransactionItem>) {
    data class TransactionsDisplayModel(val something:String) {

    }
}