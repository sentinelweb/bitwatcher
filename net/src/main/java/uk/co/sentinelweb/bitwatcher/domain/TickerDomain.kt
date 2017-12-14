package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal
import java.util.*

data class TickerDomain(val name: String = BASIC, val from: Date, val last: BigDecimal, val currencyCode: CurrencyCode, val baseCurrencyCode: CurrencyCode) {
    companion object {
        val BASIC = "basic"
        val NONE = "none"
        val INVALID = TickerDomain(NONE, Date(), BigDecimal.ZERO,CurrencyCode.UNKNOWN,CurrencyCode.UNKNOWN)
    }
}