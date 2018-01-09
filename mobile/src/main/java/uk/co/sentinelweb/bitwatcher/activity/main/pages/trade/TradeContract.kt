package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import uk.co.sentinelweb.bitwatcher.activity.main.pages.PagePresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputPresenterFactory

interface TradeContract{

    interface View{
        fun setData(model: TradeState.TradeDisplayModel)
        fun setViewPresenter(p:Presenter)
        fun getInputPresenter(inputPresenterFactory: TradeInputPresenterFactory, isBuy:Boolean): TradeInputContract.Presenter
        fun showTabContent(isBuy: Boolean)
        fun showAccountSeletor(accounts: Array<String>)
        fun showMarketsSelector(marketNames: Array<String>)
    }

    interface Presenter : PagePresenter {
        fun onAccountButtonClick()
        fun onMarketButtonClick()
        fun onTabClicked(isBuy: Boolean)
        fun onAccountSelected(index: Int)
        fun onMarketSelected(index: Int)

    }
}