package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionItemModel
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionFilterDomain

data class TransactionsState(
        val transactionList: MutableList<TransactionItemModel> = mutableListOf(),
        val accountList: MutableList<AccountDomain> = mutableListOf(),
        var filter: TransactionFilterDomain? = null,
        var summary : String? = null) {

    data class TransactionsDisplayModel(val summary: String?)
}