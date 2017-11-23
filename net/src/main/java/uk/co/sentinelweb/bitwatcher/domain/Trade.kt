package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.*

enum class TradeType {
    UNKNOWN, BID, ASK
}
data class Trade(val date: Date,
                       val tid: String,
                       val type: TradeType,
                       val price: BigDecimal,
                       val amount: BigDecimal,
                       val currencyCodeFrom:CurrencyCode,
                       val currencyCodeTo:CurrencyCode,
                       val feesAmount:BigDecimal,
                       val feesCurrencyCode: String)

// OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id, String orderId, BigDecimal feeAmount, Currency feeCurrency