package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.bitwatcher.common.extensions.div
import uk.co.sentinelweb.domain.*
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class TradeInputPresenter constructor(
        private val view: TradeInputContract.View,
        private val interactions: TradeInputContract.Interactions,
        private val mapper: TradeInputDisplayMapper,
        tradeType: TransactionItemDomain.TradeDomain.TradeType,
        private val state: TradeInputState = TradeInputState()
) : TradeInputContract.Presenter {

    init {
        state.tradeType = tradeType
        view.setData(mapper.mapModel(state), null)
    }

    override fun onAmountChanged(amountString: String) {
        try {
            state.amount = BigDecimal(amountString)
        } catch (nf: NumberFormatException) {
            state.amount = ZERO
        }
        calculateAmounts()
        view.setData(mapper.mapModel(state), TradeInputState.Field.AMOUNT)
    }

    override fun onPriceChanged(priceString: String) {
        try {
            state.price = BigDecimal(priceString)
        } catch (nf: NumberFormatException) {
            state.price = ZERO
        }
        calculateAmounts()
        view.setData(mapper.mapModel(state), TradeInputState.Field.PRICE)
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
        view.setData(mapper.mapModel(state), null)
    }

    override fun toggleLinkCurrentPrice() {
        state.linkedPrice = !state.linkedPrice
        if (state.linkedPrice) {
            state.price = state.currentPrice
        }
        calculateAmounts()
        view.setData(mapper.mapModel(state), null)
    }

    override fun onExecuteButtonClick() {
        // TODO convert currency if other
        interactions.onExecutePressed(AmountDomain(state.amount, state.amountCurrency), state.price, state.tradeType)
    }

    override fun setMarketAndAccount(account: AccountDomain?, market: CurrencyPair) {
        state.account = account
        state.market = market
        state.amountCurrency = market.currency
        view.setData(mapper.mapModel(state), null)
    }

    override fun setCurrentPrice(currentPrice: BigDecimal) {
        state.currentPrice = currentPrice
        if (state.linkedPrice) {
            state.price = currentPrice
        }
        calculateAmounts()
        view.setData(mapper.mapModel(state), null)
    }


     private fun calculateAmounts() {
        val otherAmount: BigDecimal
        if (state.amountCurrency == state.market.currency) {
            otherAmount = state.amount * state.price
        } else {
            otherAmount = state.amount * state.price.div(state.price)
        }
        state.otherAmount = otherAmount
    }


}