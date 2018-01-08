package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import uk.co.sentinelweb.bitwatcher.activity.main.pages.PagePresenter

interface TradeContract{

    interface View{
        fun setData(model: TradeState.TradeDisplayModel)
    }

    interface Presenter : PagePresenter {
        fun onAccountButtonClick()
        fun onMarketButtonClick()

    }
}