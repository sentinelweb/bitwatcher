package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.use_case.TickerUseCase
import javax.inject.Inject

class TradeInputPresenterFactory @Inject constructor(
        private val tickerUseCase: TickerUseCase
        ){

    fun createPresenter(view: TradeInputContract.View):TradeInputContract.Presenter {
        val tradeInputPresenter = TradeInputPresenter(view, tickerUseCase)
        view.setViewPresenter(tradeInputPresenter)
        return tradeInputPresenter
    }
}