package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TradeRowView
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactionRowContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactionRowPresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactionRowView
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain

class TransactionListAdapter constructor(
        var list: List<TransactionItemModel>
) : RecyclerView.Adapter<TransactionListAdapter.TransactionItemViewHolder>() {

    inner class TransactionItemViewHolder(
            itemView: View,
            val presenter:TransactionRowContract.Presenter = TransactionRowPresenter(itemView as TransactionRowContract.View)
    ) : RecyclerView.ViewHolder(itemView)

    fun setItems(list: List<TransactionItemModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
        val view = when (viewType) {
            0 -> TransactionRowView(parent.context)
            1 -> TradeRowView(parent.context)
            else -> View(parent.context) // should never happen
        }
        view.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        return TransactionItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TransactionItemViewHolder, position: Int) {
        holder.presenter.bindData(list.get(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (list.get(position).domain) {
            is TransactionDomain -> 0
            is TradeDomain -> 1
        }
    }
}