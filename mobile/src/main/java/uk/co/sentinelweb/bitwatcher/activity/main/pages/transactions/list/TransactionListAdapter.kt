package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TradeRowView
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactionRowContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactionRowPresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactionRowView
import uk.co.sentinelweb.domain.TradeDomain
import uk.co.sentinelweb.domain.TransactionDomain
import uk.co.sentinelweb.domain.TransactionItemDomain

class TransactionListAdapter constructor(
        var list: List<TransactionItemDomain>
) : RecyclerView.Adapter<TransactionListAdapter.TransactionItemViewHolder>() {

    inner class TransactionItemViewHolder(
            itemView: View,
            val presenter:TransactionRowContract.Presenter = TransactionRowPresenter(itemView as TransactionRowContract.View)
    ) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
        val view = when (viewType) {
            0 -> TransactionRowView(parent.context)
            1 -> TradeRowView(parent.context)
            else -> View(parent.context)
        }
        return TransactionItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TransactionItemViewHolder, position: Int) {
        holder.presenter.bindData(list.get(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (list.get(position)) {
            is TransactionDomain -> 0
            is TradeDomain -> 1
            else -> -1
        }
    }
}