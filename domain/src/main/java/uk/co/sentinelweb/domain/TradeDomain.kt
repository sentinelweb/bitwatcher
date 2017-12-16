package uk.co.sentinelweb.domain

import java.math.BigDecimal
import java.util.*


data class TradeDomain(override val date: Date,
                       val tid: String,
                       val type: TradeType,
                       val price: BigDecimal,
                       override val amount: BigDecimal,
                       val currencyCodeFrom:CurrencyCode,
                       val currencyCodeTo:CurrencyCode,
                       val feesAmount:BigDecimal,
                       val feesCurrencyCode: String) : TransactionItemDomain(date, amount, currencyCodeFrom) {
    enum class TradeType {
        UNKNOWN, BID, ASK
    }
}

// OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id, String orderId, BigDecimal feeAmount, Currency feeCurrency