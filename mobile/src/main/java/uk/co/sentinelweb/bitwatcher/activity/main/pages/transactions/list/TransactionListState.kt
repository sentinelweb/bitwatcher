package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list


data class TransactionListState(
        var transactions: List<TransactionItemModel>
) {
    data class TransactionItemListModel(
            val transactions: List<TransactionItemModel>)
}
