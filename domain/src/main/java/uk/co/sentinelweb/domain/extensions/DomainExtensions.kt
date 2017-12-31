package uk.co.sentinelweb.domain.extensions

import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.domain.TickerDomain

fun TickerDomain.getPairKey(): String = CurrencyPair.getKey(this.currencyCode, this.baseCurrencyCode)

