package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.main_transactions_page.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListPresenter
import kotlinx.android.synthetic.main.transaction_filter_bottom_sheet.view.*
import android.support.design.widget.BottomSheetBehavior
import android.view.View


class TransactionsView(context: Context) : FrameLayout(context), TransactionsContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.main_transactions_page, this, true)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
//        bottomSheetBehavior.setPeekHeight(resources.getInteger(R.integer.bottom_sheet_open_height_dp))
//        bottomSheetBehavior.setHideable(true)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    transactions_filter_fab.visibility = View.GONE
                } else {
                    transactions_filter_fab.visibility = View.VISIBLE
                }
            }

        })
        transactions_filter_fab.setOnClickListener({ _ -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED) })
    }

    override fun setData(model: TransactionsState.TransactionsModel) {

    }

    override fun getListPresenter(): TransactionListContract.Presenter {
        return TransactionListPresenter(transactions_list)
    }

}