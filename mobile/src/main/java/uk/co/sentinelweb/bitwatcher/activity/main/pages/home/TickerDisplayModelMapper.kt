package uk.co.sentinelweb.bitwatcher.activity.main.pages.home

import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain
import javax.inject.Inject

class TickerDisplayModelMapper @Inject constructor() {

    fun map(t: TickerDomain?, state: HomeState.TickerDisplay): HomeState.TickerDisplay {
        when (t?.currencyCode) {
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
           CurrencyCode.XRP ->
                when (t.baseCurrencyCode) {
                    CurrencyCode.USD -> state.xrpUsdPriceText = t.last.dp(2)
                    CurrencyCode.EUR -> state.xrpEurPriceText = t.last.dp(2)
                    CurrencyCode.GBP -> state.xrpGbpPriceText = t.last.dp(2)
                    else -> {
                    }
                }
           CurrencyCode.IOTA ->
                when (t.baseCurrencyCode) {
                    CurrencyCode.USD -> state.iotaUsdPriceText = t.last.dp(2)
                    CurrencyCode.EUR -> state.iotaEurPriceText = t.last.dp(2)
                    CurrencyCode.GBP -> state.iotaGbpPriceText = t.last.dp(2)
                    else -> {
                    }
                }
            else -> {
            }
        }
        return state
    }
}