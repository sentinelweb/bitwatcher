package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain

data class TransactionsState(
        val transactionList: MutableList<TransactionItemDomain> = mutableListOf(),
        val accountList: MutableList<AccountDomain> = mutableListOf()) {
    data class TransactionsModel(val transactionList: List<TransactionItemDomain>) {

    }
}