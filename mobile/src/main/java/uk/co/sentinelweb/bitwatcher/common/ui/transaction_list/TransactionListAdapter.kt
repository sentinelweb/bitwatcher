package uk.co.sentinelweb.bitwatcher.common.ui.transaction_list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.row.TradeRowView
import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.row.TransactionRowContract
import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.row.TransactionRowPresenter
import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.row.TransactionRowView
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain

class TransactionListAdapter constructor(
        var list: List<TransactionItemModel>
) : RecyclerView.Adapter<TransactionListAdapter.TransactionItemViewHolder>(), TransactionRowContract.Interactions {

    val selection: MutableSet<TransactionItemModel> = mutableSetOf()
    lateinit var interactions:Interactions
    inner class TransactionItemViewHolder(
            itemView: View,
            val presenter: TransactionRowContract.Presenter = TransactionRowPresenter(itemView as TransactionRowContract.View, this@TransactionListAdapter)
    ) : RecyclerView.ViewHolder(itemView)

    interface Interactions {
        fun onSelectionChanged(selection: Set<TransactionItemModel>)
    }

    fun setItems(list: List<TransactionItemModel>) {
        this.list = list
        val filtered = selection.filter { model -> list.contains(model) }
        selection.clear()
        selection.addAll(filtered)
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
        val model = list.get(position)
        holder.presenter.bindData(model, selection.contains(model))
    }

    override fun getItemViewType(position: Int): Int {
        return when (list.get(position).domain) {
            is TransactionDomain -> 0
            is TradeDomain -> 1
        }
    }

    override fun onSelect(model: TransactionItemModel) {
        if (selection.contains(model)) {
            selection.remove(model)
        } else {
            selection.add(model)
        }
        interactions.onSelectionChanged(selection)
    }

}