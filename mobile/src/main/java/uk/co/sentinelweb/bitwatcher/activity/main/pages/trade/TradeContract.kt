package uk.co.sentinelweb.bitwatcher.activity.pages.trade

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface TradeContract {

    interface View {
        fun setData(model: TradeModel)
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}