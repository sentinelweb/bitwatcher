package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R

class TransactionsView(context: Context?): FrameLayout(context), TransactionsContract.View {

    init {
        LayoutInflater.from(context).inflate(R.layout.main_transactions_page, this, true)
    }

    override fun setData(model: TransactionsState.TransactionsDisplayModel) {

    }
}