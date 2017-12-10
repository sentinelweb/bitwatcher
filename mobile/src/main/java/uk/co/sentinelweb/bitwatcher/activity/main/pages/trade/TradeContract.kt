package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface TradeContract {

    interface View {
        fun setData(state: TradeState)
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}