package uk.co.sentinelweb.bitwatcher.common.ui.transaction_list

interface TransactionListContract {
    interface View {
        fun setData(list: TransactionListState.TransactionItemListModel)
        fun setInteractions(interactions: TransactionListAdapter.Interactions)
    }

    interface Presenter {
        fun bindData(list: List<TransactionItemModel>)
        fun setInteractions(interactions: Interactions)
    }

    interface Interactions : TransactionListAdapter.Interactions

}