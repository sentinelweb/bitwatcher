package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import uk.co.sentinelweb.bitwatcher.activity.main.pages.PagePresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterPresenterFactory
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListContract

interface TransactionsContract {

    interface View {
        fun setData(model: TransactionsState.TransactionsModel)
        fun getListPresenter(): TransactionListContract.Presenter
        fun getFilterPresenter(filterPresenterFactory: TransactionFilterPresenterFactory):TransactionFilterContract.Presenter
        fun showLoading(show:Boolean)
        fun closeFilter()
        fun collapseFilter()
    }

    interface Presenter : PagePresenter {

    }
}