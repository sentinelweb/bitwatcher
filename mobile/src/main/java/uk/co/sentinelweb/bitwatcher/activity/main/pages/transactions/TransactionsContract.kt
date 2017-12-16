package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListContract
import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface TransactionsContract {

    interface View {
        fun setData(model: TransactionsState.TransactionsModel)
        fun getListPresenter(): TransactionListContract.Presenter
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}