package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyPair

interface TradeInputContract {

    interface View {
        fun setData(model:TradeInputState.TradeInputDisplayModel)
        fun setViewPresenter(tradeInputPresenter: Presenter)
    }

    interface Presenter {
        fun onCurrencyButtonClick()
        fun onExecuteButtonClick()
        fun onAmountChanged(amountString: String)
        fun onPriceChanged(priceString: String)
        fun setMarketAndAccount(account: AccountDomain?, market: CurrencyPair)

    }
}