package uk.co.sentinelweb.bitwatcher.activity.pages.home

data class HomeState(val tickerState:TickerState = TickerState()) {

    data class TickerState(
            var btcUsdPriceText: String = "-",
            var ethUsdPriceText: String = "-",
            var bchUsdPriceText: String = "-",
            var btcGbpPriceText: String = "-",
            var ethGbpPriceText: String = "-",
            var bchGbpPriceText: String = "-",
            var btcEurPriceText: String = "-",
            var ethEurPriceText: String = "-",
            var bchEurPriceText: String = "-")
}