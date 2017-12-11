package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal
import java.util.*


data class TradeDomain(val date: Date,
                       val tid: String,
                       val type: TradeType,
                       val price: BigDecimal,
                       val amount: BigDecimal,
                       val currencyCodeFrom:CurrencyCode,
                       val currencyCodeTo:CurrencyCode,
                       val feesAmount:BigDecimal,
                       val feesCurrencyCode: String) {
    enum class TradeType {
        UNKNOWN, BID, ASK
    }
}

// OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id, String orderId, BigDecimal feeAmount, Currency feeCurrency