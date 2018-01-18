package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.bitwatcher.common.mapper.StringMapper
import uk.co.sentinelweb.domain.TransactionItemDomain
import javax.inject.Inject

class TradeInputPresenterFactory @Inject constructor(
        private val stringMapper: StringMapper
) {

    fun createPresenter(view: TradeInputContract.View, interactions: TradeInputContract.Interactions, type: TransactionItemDomain.TradeDomain.TradeType):TradeInputContract.Presenter {
        val tradeInputPresenter = TradeInputPresenter(view, interactions, TradeInputDisplayMapper(stringMapper), type)
        view.setPresenter(tradeInputPresenter)
        return tradeInputPresenter
    }
}