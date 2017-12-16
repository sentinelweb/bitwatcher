package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

import uk.co.sentinelweb.domain.TransactionItemDomain


data class TransactionListState(
        var transactions: List<TransactionItemDomain>
) {
    data class TransactionItemListModel(
            val transactions: List<TransactionItemDomain>)
}
