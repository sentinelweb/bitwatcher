package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_transaction_row_trade.view.*
import uk.co.sentinelweb.bitwatcher.R

class TradeRowView(context: Context) : FrameLayout(context), TransactionRowContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_transaction_row_trade, this, true)
    }

    override fun setData(model: TransactonRowState.DisplayModel) {
        if (model is TransactonRowState.DisplayModel.TradeDisplayModel) {
            icon.setImageResource(model.icon)
            icon.setColorFilter(ContextCompat.getColor(context, model.iconColor))
            amount_market.text = model.amount
            fee.text = model.feeAmount
            trade_type.text = model.type
            market_name.text = model.market
            quantity_rate.text = model.quantityAndRate
            date.text = model.date
            trade_id.text = model.id
            account.text = model.accountName
            account.background = ColorDrawable(model.accountColor)
            trade_status.text = model.status
        }
    }
}