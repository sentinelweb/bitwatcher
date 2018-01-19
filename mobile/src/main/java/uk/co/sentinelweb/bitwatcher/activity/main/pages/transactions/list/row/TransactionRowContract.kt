package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionItemModel

interface TransactionRowContract {
    interface View {
        fun setData( model:TransactonRowState.DisplayModel)
        fun setPresenter(presenter:Presenter)
    }

    interface Presenter {
        fun bindData(model: TransactionItemModel, selected: Boolean)
        fun onSelect()
    }

    interface Interactions {
        fun onSelect(model: TransactionItemModel)
    }
}