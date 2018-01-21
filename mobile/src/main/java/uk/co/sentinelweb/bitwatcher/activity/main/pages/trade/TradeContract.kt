package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import uk.co.sentinelweb.bitwatcher.activity.main.pages.PagePresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputPresenterFactory
import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.TransactionListContract
import uk.co.sentinelweb.domain.TransactionItemDomain

interface TradeContract{

    interface View {
        fun setData(model: TradeState.TradeDisplayModel)
        fun setPresenter(p:Presenter)
        fun getInputPresenter(inputPresenterFactory: TradeInputPresenterFactory,
                              interactions: TradeInputContract.Interactions,
                              type: TransactionItemDomain.TradeDomain.TradeType)
                : TradeInputContract.Presenter
        fun showTabContent(tab: Tab)
        fun showAccountSeletor(accounts: Array<String>)
        fun showMarketsSelector(marketNames: Array<String>)
        fun getListPresenter(): TransactionListContract.Presenter

        enum class Tab {
            OPEN_TRADES, BUY, SELL
        }
    }

    interface Presenter : PagePresenter {
        fun onAccountButtonClick()
        fun onMarketButtonClick()
        fun onTabClicked(tab: View.Tab)
        fun onAccountSelected(index: Int)
        fun onMarketSelected(index: Int)

    }
}