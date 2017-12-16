package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.row

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R

class TransactionRowView(context: Context?): FrameLayout(context), TransactionRowContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.main_transactions_page, this, true)
    }

    override fun setData(model: TransactonRowState.TransactionRowDisplayModel) {
    }
}