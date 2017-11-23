package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class TickerData(val from: Date, val last: BigDecimal, val currencyCode: CurrencyCode, val baseCurrencyCode: CurrencyCode) {
}