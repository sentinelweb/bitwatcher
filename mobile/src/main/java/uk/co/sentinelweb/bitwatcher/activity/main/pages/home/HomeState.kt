package uk.co.sentinelweb.bitwatcher.activity.main.pages.home

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain

class HomeState(
        var prices: MutableMap<String, TickerDomain> = mutableMapOf(),
        var accounts: List<AccountDomain> = listOf(),
        val tickerDisplay: TickerDisplay = TickerDisplay(),
        val totals: TotalsDisplay = TotalsDisplay(),
        val selectedAccountIds: MutableSet<Long> = mutableSetOf(),
        var displayCurrency: CurrencyCode = CurrencyCode.GBP,
        var displayRealItems: Boolean = true,
        var deletedAccount: AccountDomain? = null
) {

    data class TickerDisplay(
            var btcUsdPriceText: String = "-",
            var ethUsdPriceText: String = "-",
            var bchUsdPriceText: String = "-",
            var btcGbpPriceText: String = "-",
            var ethGbpPriceText: String = "-",
            var bchGbpPriceText: String = "-",
            var btcEurPriceText: String = "-",
            var ethEurPriceText: String = "-",
            var bchEurPriceText: String = "-",
            var xrpUsdPriceText: String = "-",
            var xrpGbpPriceText: String = "-",
            var xrpEurPriceText: String = "-",
            var iotaUsdPriceText: String = "-",
            var iotaGbpPriceText: String = "-",
            var iotaEurPriceText: String = "-"
    )

    data class TotalsDisplay(
            var realTotalDisplay: String = "-",
            var ghostTotalDisplay: String = "-"
    )

}