package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R
import kotlinx.android.synthetic.main.view_transaction_row_transaction.view.*
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactonRowState.DisplayModel.TransactionDisplayModel

class TransactionRowView(context: Context?): FrameLayout(context), TransactionRowContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_transaction_row_transaction, this, true)
    }

    override fun setData(model: TransactonRowState.DisplayModel) {
        if (model is TransactionDisplayModel) {
            icon.setImageResource(model.icon)
            icon.setColorFilter(ContextCompat.getColor(context, model.iconColor))
            amount_market.text = model.amount
            fee.text = model.feeAmount
            transaction_type.text = model.type
            transaction_status.text = model.status
            date.text = model.date
            trans_id.text = model.id
        }
    }
}