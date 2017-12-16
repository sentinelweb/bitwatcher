package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

import uk.co.sentinelweb.domain.TransactionItemDomain

interface TransactionListContract {
    interface View {
        fun setData(list:TransactionListState.TransactionItemListModel)
    }

    interface Presenter {
        fun bindData(list:List<TransactionItemDomain>)
    }
}