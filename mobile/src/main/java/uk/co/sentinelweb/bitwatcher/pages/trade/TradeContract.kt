package uk.co.sentinelweb.bitwatcher.pages.trade

import uk.co.sentinelweb.bitwatcher.pages.PagePresenter
import uk.co.sentinelweb.bitwatcher.pages.trade.TradeModel

interface TradeContract {

    interface View {
        fun setData(model: TradeModel)
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}