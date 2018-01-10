package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.domain.TransactionItemDomain
import javax.inject.Inject

class TradeInputPresenterFactory @Inject constructor() {

    fun createPresenter(view: TradeInputContract.View, interactions: TradeInputContract.Interactions, type: TransactionItemDomain.TradeDomain.TradeType):TradeInputContract.Presenter {
        val tradeInputPresenter = TradeInputPresenter(view, interactions, type)
        view.setPresenter(tradeInputPresenter)
        return tradeInputPresenter
    }
}