package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R

class TradeRowView(context: Context?): FrameLayout(context), TransactionRowContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_transaction_row_trade, this, true)
    }

    override fun setData(model: TransactonRowState.TransactionRowDisplayModel) {
    }
}