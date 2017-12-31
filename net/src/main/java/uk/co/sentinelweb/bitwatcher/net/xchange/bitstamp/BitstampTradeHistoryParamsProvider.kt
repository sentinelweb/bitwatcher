package uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp

import org.knowm.xchange.bitstamp.service.BitstampTradeHistoryParams
import org.knowm.xchange.service.trade.params.TradeHistoryParams
import uk.co.sentinelweb.bitwatcher.net.xchange.CurrencyPairLookup
import uk.co.sentinelweb.bitwatcher.net.xchange.generic.TradeHistoryParamsProvider
import uk.co.sentinelweb.domain.CurrencyPair

class BitstampTradeHistoryParamsProvider : TradeHistoryParamsProvider {
    override fun provide(pair: CurrencyPair?): TradeHistoryParams {
        val currencyPair = if (pair != null) CurrencyPairLookup.lookup(pair) else null
        val bitstampTradeHistoryParams = BitstampTradeHistoryParams(currencyPair, 100)
        bitstampTradeHistoryParams.pageNumber = 0
        return bitstampTradeHistoryParams
    }
}