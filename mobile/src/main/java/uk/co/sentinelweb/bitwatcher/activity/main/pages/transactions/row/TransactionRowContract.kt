package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.row

import uk.co.sentinelweb.domain.TransactionItem

interface TransactionRowContract {
    interface View {
        fun setData( model:TransactonRowState.TransactionRowDisplayModel)
    }

    interface Presenter {
        fun bindData(domain:TransactionItem)
    }
}