package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal
import java.time.Instant

enum class OrderType {
    UNKNOWN, BUY, SELL, DEPOSIT, WITHDRAW
}
data class Transaction( val date: Instant,
                        val tid: String,
                        val type: OrderType,
                        val price: BigDecimal,
                        val amount: BigDecimal,
                        val currencyCodeFrom:String,
                        val currencyCodeTo:String,
                        val feesAmount:BigDecimal,
                        val feesCurrencyCode: String)

// OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id, String orderId, BigDecimal feeAmount, Currency feeCurrency