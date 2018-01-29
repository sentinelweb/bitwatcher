package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputState.TradeInputDisplayModel
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.bitwatcher.common.mapper.AmountFormatter
import uk.co.sentinelweb.bitwatcher.common.mapper.StringMapper
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType.BID
import java.math.BigDecimal

class TradeInputDisplayMapper constructor(
        private val stringMapper: StringMapper,
        private val currencyFormatter:AmountFormatter
) {

    fun mapModel(state: TradeInputState): TradeInputDisplayModel {
        return TradeInputDisplayModel(
                zero(state.amount),
                zero(state.price, 5),
                true,
                currencyFormatter.format(state.otherAmount, state.otherCurrency),
                "help",
                stringMapper.getString(if (state.tradeType == BID) R.string.buy else R.string.sell),
                state.amount > BigDecimal.ZERO,
                if (state.tradeType == BID) R.color.colorBuy else R.color.colorSell,
                state.amountCurrency.toString()
        )
    }

    private fun zero(amount: BigDecimal, scale: Int = 2): String {
        if (amount == BigDecimal.ZERO) return "0"
        else return amount.dp(scale)
    }

}