package uk.co.sentinelweb.bitwatcher.domain.extensions

import uk.co.sentinelweb.bitwatcher.domain.TickerDomain

fun TickerDomain.getPairKey(): String = this.currencyCode.toString() + "." + this.baseCurrencyCode.toString()
