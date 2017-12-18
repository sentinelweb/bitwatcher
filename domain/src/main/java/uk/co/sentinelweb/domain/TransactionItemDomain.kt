package uk.co.sentinelweb.domain

import java.math.BigDecimal
import java.util.*

sealed class TransactionItemDomain constructor(open val id: String,
                                             open val date: Date,
                                             open val amount: BigDecimal,
                                             open val currencyCode: CurrencyCode) {
    data class TransactionDomain(
            override val id: String,
            val type: TransactionType,
            override val date: Date,
            override val amount: BigDecimal,
            override val currencyCode: CurrencyCode,
            val balance: BigDecimal,
            val description: String,
            val status: TransactionStatus,
            val fee: BigDecimal
    ) : TransactionItemDomain(id, date, amount, currencyCode){

        enum class TransactionType {
            DEPOSIT,
            WITHDRAWL,
            UNKNOWN

        }

        enum class TransactionStatus {
            PROCESSING,
            COMPLETE,
            CANCELLED,
            FAILED,
            UNKNOWN
        }
    }

    data class TradeDomain(override val date: Date,
                           val tid: String,
                           val type: TradeType,
                           val price: BigDecimal,
                           override val amount: BigDecimal,
                           val currencyCodeFrom:CurrencyCode,
                           val currencyCodeTo:CurrencyCode,
                           val feesAmount:BigDecimal,
                           val feesCurrencyCode: String
    ) : TransactionItemDomain(tid, date, amount, currencyCodeFrom) {
        enum class TradeType {
            UNKNOWN, BID, ASK
        }
    }
}
