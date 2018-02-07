package uk.co.sentinelweb.domain

import java.math.BigDecimal
import java.util.*

data class TickerDomain(val name: String = NAME_CURRENT, val from: Date, val last: BigDecimal, val currencyCode: CurrencyCode, val baseCurrencyCode: CurrencyCode) {
    companion object {
        val NAME_CURRENT = "current"
        val NAME_PREVIOUS = "previous"
        val NONE = "none"
        val INVALID = TickerDomain(NONE, Date(), BigDecimal.ZERO, CurrencyCode.UNKNOWN, CurrencyCode.UNKNOWN)
    }
}