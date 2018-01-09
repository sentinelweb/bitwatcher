package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.use_case.TickerUseCase

class TradeInputPresenter constructor(
        private val view: TradeInputContract.View,
        private val tickerUseCase: TickerUseCase,
        private val state: TradeInputState = TradeInputState()
) : TradeInputContract.Presenter {

    init {

    }

    override fun onAmountChanged(amountString: String) {

    }

    override fun onPriceChanged(priceString: String) {

    }

    override fun onCurrencyButtonClick() {
    }

    override fun onExecuteButtonClick() {
    }

    override fun setMarketAndAccount(account: AccountDomain?, market: CurrencyPair) {
        state.account = account
        state.market = market
        view.setData(mapModel())
    }

    fun mapModel(): TradeInputState.TradeInputDisplayModel {
        return TradeInputState.TradeInputDisplayModel(
                state.amount.dp(2),
                state.price.dp(2),
                true,
                "amount to Trade",
                "help",
                "Buy or sell",
                false
                )
    }
}