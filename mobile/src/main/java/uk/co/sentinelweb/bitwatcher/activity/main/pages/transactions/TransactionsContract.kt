package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface TransactionsContract {

    interface View {
        fun setData(model: TransactionsState.TransactionsDisplayModel)
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}