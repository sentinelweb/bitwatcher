package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_transaction_row_transaction.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactonRowState.DisplayModel.TransactionDisplayModel

class TransactionRowView(context: Context?): FrameLayout(context), TransactionRowContract.View {
    private lateinit var presenter:TransactionRowContract.Presenter

    init {
        LayoutInflater.from(context).inflate(R.layout.view_transaction_row_transaction, this, true)
        setOnClickListener({presenter.onSelect()})
    }

    override fun setPresenter(presenter: TransactionRowContract.Presenter) {
        this.presenter = presenter
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
            account.text = model.accountName
            account.background = ColorDrawable(model.accountColor)
            setBackgroundColor(ContextCompat.getColor(context, model.backgroundColor))
        }
    }
}