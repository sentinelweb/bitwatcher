package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterPresenterFactory
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListContract
import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface TransactionsContract {

    interface View {
        fun setData(model: TransactionsState.TransactionsModel)
        fun getListPresenter(): TransactionListContract.Presenter
        fun getFilterPresenter(filterPresenterFactory: TransactionFilterPresenterFactory):TransactionFilterContract.Presenter
        fun showLoading(show:Boolean)
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}