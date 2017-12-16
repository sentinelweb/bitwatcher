package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.main_transactions_page.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListPresenter

class TransactionsView(context: Context): FrameLayout(context), TransactionsContract.View {

    init {
        LayoutInflater.from(context).inflate(R.layout.main_transactions_page, this, true)

    }

    override fun setData(model: TransactionsState.TransactionsModel) {

    }

    override fun getListPresenter(): TransactionListContract.Presenter {
        return TransactionListPresenter(transaction_list)
    }

}