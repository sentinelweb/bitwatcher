package uk.co.sentinelweb.bitwatcher.activity.pages.home

import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.TickerData
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class TickerStateMapper @Inject constructor() {
    fun BigDecimal.dp(scale: Int = 2): String = this.setScale(scale, RoundingMode.HALF_EVEN).toDouble().toString()

    fun map(t: TickerData, state: HomeState.TickerState): HomeState.TickerState {
        when (t.currencyCode) {
            CurrencyCode.ETH ->
                when (t.baseCurrencyCode) {
                    CurrencyCode.USD -> state.ethUsdPriceText = t.last.dp(2)
                    CurrencyCode.EUR -> state.ethEurPriceText = t.last.dp(2)
                    CurrencyCode.GBP -> state.ethGbpPriceText = t.last.dp(2)
                    else -> {
                    }
                }
            CurrencyCode.BTC ->
                when (t.baseCurrencyCode) {
                    CurrencyCode.USD -> state.btcUsdPriceText = t.last.dp(2)
                    CurrencyCode.EUR -> state.btcEurPriceText = t.last.dp(2)
                    CurrencyCode.GBP -> state.btcGbpPriceText = t.last.dp(2)
                    else -> {
                    }
                }
            CurrencyCode.BCH ->
                when (t.baseCurrencyCode) {
                    CurrencyCode.USD -> state.bchUsdPriceText = t.last.dp(2)
                    CurrencyCode.EUR -> state.bchEurPriceText = t.last.dp(2)
                    CurrencyCode.GBP -> state.bchGbpPriceText = t.last.dp(2)
                    else -> {
                    }
                }
            else -> {
            }
        }
        return state
    }
}