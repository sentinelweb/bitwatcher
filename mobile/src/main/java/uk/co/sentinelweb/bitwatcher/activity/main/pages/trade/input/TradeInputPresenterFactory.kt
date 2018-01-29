package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.bitwatcher.common.mapper.AmountFormatter
import uk.co.sentinelweb.bitwatcher.common.mapper.StringMapper
import uk.co.sentinelweb.domain.TransactionItemDomain
import javax.inject.Inject

class TradeInputPresenterFactory @Inject constructor(
        private val stringMapper: StringMapper,
        private val currencyFormatter: AmountFormatter
) {

    fun createPresenter(view: TradeInputContract.View, interactions: TradeInputContract.Interactions, type: TransactionItemDomain.TradeDomain.TradeType):TradeInputContract.Presenter {
        val tradeInputPresenter = TradeInputPresenter(view, interactions, TradeInputDisplayMapper(stringMapper, currencyFormatter), type)
        view.setPresenter(tradeInputPresenter)
        return tradeInputPresenter
    }
}