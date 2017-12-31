package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

interface TransactionListContract {
    interface View {
        fun setData(list:TransactionListState.TransactionItemListModel)
    }

    interface Presenter {
        fun bindData(list:List<TransactionItemModel>)
    }
}