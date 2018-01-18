package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.domain.*
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType
import java.math.BigDecimal

interface TradeInputContract {

    interface View {
        fun setData(model:TradeInputState.TradeInputDisplayModel)
        fun setPresenter(tradeInputPresenter: Presenter)
        fun showCurrencySelector(currencies: Array<String>)
    }

    interface Presenter {
        fun onCurrencyButtonClick()
        fun onExecuteButtonClick()
        fun onAmountChanged(amountString: String)
        fun onPriceChanged(priceString: String)
        fun setMarketAndAccount(account: AccountDomain?, market: CurrencyPair)
        fun setCurrentPrice(currentPrice: BigDecimal)
        fun onAmountCurrencySelected(currencyString: String)
        fun toggleLinkCurrentPrice()
    }

    interface Interactions {
        fun onExecutePressed(amount: AmountDomain, price: BigDecimal, type: TradeType)
    }
}