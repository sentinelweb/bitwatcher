package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_transaction_list.view.*
import uk.co.sentinelweb.bitwatcher.R

class TransactionListView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet), TransactionListContract.View {

    private var adapter = TransactionListAdapter(listOf())

    init {
        LayoutInflater.from(context).inflate(R.layout.view_transaction_list, this, true)
        transaction_list_recyclerview.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        dividerItemDecoration.setDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.grey_400)))
        transaction_list_recyclerview.addItemDecoration(dividerItemDecoration)
        transaction_list_recyclerview.adapter = adapter
    }

    override fun setData(list: TransactionListState.TransactionItemListModel) {
        adapter.setItems(list.transactions)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        super.onRestoreInstanceState(state)
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        return Bundle()
    }

    override fun setInteractions(interactions: TransactionListAdapter.Interactions) {
        adapter.interactions = interactions
    }

}