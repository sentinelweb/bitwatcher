package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import uk.co.sentinelweb.domain.TransactionItemDomain

interface TransactionRowContract {
    interface View {
        fun setData( model:TransactonRowState.DisplayModel)
    }

    interface Presenter {
        fun bindData(domain: TransactionItemDomain)
    }
}