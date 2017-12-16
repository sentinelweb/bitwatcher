package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_transaction_list.view.*
import uk.co.sentinelweb.bitwatcher.R

class TransactionListView(context: Context, attributeSet: AttributeSet): FrameLayout(context,attributeSet), TransactionListContract.View {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_transaction_list, this, true)
        transaction_list_recyclerview.layoutManager = LinearLayoutManager(context)


    }

    override fun setData(list: TransactionListState.TransactionItemListModel) {
        transaction_list_recyclerview.adapter = TransactionListAdapter(list.transactions)
    }


}