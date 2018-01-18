package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputState.TradeInputDisplayModel
import uk.co.sentinelweb.bitwatcher.common.extensions.div
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.*
import java.math.BigDecimal

class TradeInputPresenter constructor(
        private val view: TradeInputContract.View,
        private val interactions: TradeInputContract.Interactions,
        tradeType:TransactionItemDomain.TradeDomain.TradeType,
        private val state: TradeInputState = TradeInputState()
) : TradeInputContract.Presenter {
    init {
        state.tradeType = tradeType;
    }

    override fun onAmountChanged(amountString: String) {
        try {
            state.amount = BigDecimal(amountString)
        } catch (nf: NumberFormatException) {
            state.amount = BigDecimal.ZERO
        }
        calculateAmounts()
        view.setData(mapModel())
    }

    override fun onPriceChanged(priceString: String) {
        try {
            state.price = BigDecimal(priceString)
        } catch (nf: NumberFormatException) {
            state.price = BigDecimal.ZERO
        }
        calculateAmounts()
        view.setData(mapModel())
    }

    override fun onCurrencyButtonClick() {
        if (state.market != CurrencyPair.NONE) {
            view.showCurrencySelector(arrayOf<String>(state.market.currency.toString(), state.market.base.toString()))
        }
    }

    override fun onAmountCurrencySelected(currencyString: String) {
        try {
            val newCode = CurrencyCode.valueOf(currencyString)
            val wasChanged = state.amountCurrency != newCode
            state.amountCurrency = newCode
            if (wasChanged) {
                state.amount = BigDecimal.ONE.div(state.amount)
            }
        } catch (e: Exception) {
        }
        calculateAmounts()
        view.setData(mapModel())
    }

    override fun toggleLinkCurrentPrice() {
        state.linkedPrice = !state.linkedPrice
        if (state.linkedPrice) {
            state.price = state.currentPrice
        }
        calculateAmounts()
        view.setData(mapModel())
    }

    override fun onExecuteButtonClick() {
        // TODO convert currency if other
        interactions.onExecutePressed(AmountDomain( state.amount,state.amountCurrency), state.price, state.tradeType)
    }

    override fun setMarketAndAccount(account: AccountDomain?, market: CurrencyPair) {
        state.account = account
        state.market = market
        state.amountCurrency = market.currency
        view.setData(mapModel())
    }

    override fun setCurrentPrice(currentPrice: BigDecimal) {
        state.currentPrice = currentPrice
        if (state.linkedPrice) {
            state.price = currentPrice
        }
    }

    fun mapModel(): TradeInputDisplayModel {
        return TradeInputDisplayModel(
                state.amount.dp(2),
                state.price.dp(5),
                true,
                state.otherAmount.dp(4),
                "help",
                state.tradeType.toString(),
                false
                )
    }

    private fun calculateAmounts() {
        val otherAmount: BigDecimal
        if (state.amountCurrency == state.market.currency) {
            otherAmount = state.amount * state.price
        } else {
            otherAmount = state.amount * state.price.div(state.price)
        }
        state.otherAmount =  otherAmount
    }


}