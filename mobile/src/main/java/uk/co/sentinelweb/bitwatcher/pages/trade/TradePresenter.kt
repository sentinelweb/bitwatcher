package uk.co.sentinelweb.bitwatcher.pages.trade

import android.view.View


class TradePresenter(val view: TradeContract.View) : TradeContract.Presenter {

        override fun view(): View {
            return view as View
        }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun loadData() {

    }
}