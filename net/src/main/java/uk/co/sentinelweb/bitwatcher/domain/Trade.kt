package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal
import java.time.Instant

enum class TradeType {
    UNKNOWN, BID, ASK
}
data class Trade(val date: Instant,
                       val tid: String,
                       val type: TradeType,
                       val price: BigDecimal,
                       val amount: BigDecimal,
                       val currencyCodeFrom:String,
                       val currencyCodeTo:String,
                       val feesAmount:BigDecimal,
                       val feesCurrencyCode: String)

// OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id, String orderId, BigDecimal feeAmount, Currency feeCurrency