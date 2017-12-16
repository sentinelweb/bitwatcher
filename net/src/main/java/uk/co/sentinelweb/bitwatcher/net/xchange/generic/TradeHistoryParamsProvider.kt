package uk.co.sentinelweb.bitwatcher.net.xchange.generic

import org.knowm.xchange.service.trade.params.TradeHistoryParams
import uk.co.sentinelweb.domain.CurrencyPair

interface TradeHistoryParamsProvider {
    fun provide(pair: CurrencyPair):TradeHistoryParams
}