package uk.co.sentinelweb.bitwatcher.common.mapper

import uk.co.sentinelweb.domain.CurrencyPair
import javax.inject.Inject

class CurrencyPairMapper @Inject constructor() {
    fun mapName(market: CurrencyPair): String {
        return "${market.currency}/${market.base}"
    }
}