package uk.co.sentinelweb.bitwatcher.common.ui.transaction_list


data class TransactionListState(
        var transactions: List<TransactionItemModel>
) {
    data class TransactionItemListModel(
            val transactions: List<TransactionItemModel>)
}
