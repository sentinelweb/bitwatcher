package uk.co.sentinelweb.domain.extensions

import uk.co.sentinelweb.domain.TickerDomain

fun TickerDomain.getPairKey(): String = this.currencyCode.toString() + "." + this.baseCurrencyCode.toString()
