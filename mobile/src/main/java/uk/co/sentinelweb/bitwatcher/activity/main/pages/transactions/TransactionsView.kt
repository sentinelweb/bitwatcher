package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.main_transactions_page.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterPresenterFactory
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterView
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListPresenter


class TransactionsView(context: Context) : FrameLayout(context), TransactionsContract.View {
    private val bottomSheetBehavior: BottomSheetBehavior<TransactionFilterView>

    init {
        LayoutInflater.from(context).inflate(R.layout.main_transactions_page, this, true)
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    transactions_filter_fab.visibility = View.GONE
                } else {
                    transactions_filter_fab.visibility = View.VISIBLE
                }
            }

        })
        transactions_filter_fab.setOnClickListener({ bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED) })

    }

    override fun closeFilter() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
    }

    override fun collapseFilter() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
    }

    override fun setData(model: TransactionsState.TransactionsModel) {

    }

    override fun getListPresenter(): TransactionListContract.Presenter {
        return TransactionListPresenter(transactions_list)
    }

    override fun getFilterPresenter(filterPresenterFactory: TransactionFilterPresenterFactory): TransactionFilterContract.Presenter {
        return filterPresenterFactory.createPresenter(bottom_sheet)
    }

    override fun showLoading(show: Boolean) {// TODO ; WTF !! doesn't show
        if (show) {
            ObjectAnimator.ofFloat(transactions_loading, "alpha",  1f).start()
            //transactions_loading.visibility = View.VISIBLE
        } else {
            ObjectAnimator.ofFloat(transactions_loading, "alpha",  0f).start()
            //transactions_loading.visibility = View.GONE
        }
    }

}