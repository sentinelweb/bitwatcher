package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionItemModel
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionFilterDomain
import uk.co.sentinelweb.domain.TransactionItemDomain

data class TransactionsState(
        val transactionList: MutableList<TransactionItemModel> = mutableListOf(),
        val accountList: MutableList<AccountDomain> = mutableListOf(),
        var filter: TransactionFilterDomain? = null) {

    data class TransactionsModel(val transactionList: List<TransactionItemDomain>)
}