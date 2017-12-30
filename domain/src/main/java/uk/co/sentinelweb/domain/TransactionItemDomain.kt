package uk.co.sentinelweb.domain

import java.math.BigDecimal
import java.util.*

sealed class TransactionItemDomain constructor(open val transactionId: String,
                                               open val date: Date,
                                               open val amount: BigDecimal,
                                               open val currencyCode: CurrencyCode) {
    data class TransactionDomain(
            override val transactionId: String,
            override val date: Date,
            override val amount: BigDecimal,
            override val currencyCode: CurrencyCode,
            val type: TransactionType,
            val balance: BigDecimal,
            val description: String,
            val status: TransactionStatus,
            val fee: BigDecimal
    ) : TransactionItemDomain(transactionId, date, amount, currencyCode) {

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

    data class TradeDomain(override val transactionId: String,
                           override val date: Date,
                           override val amount: BigDecimal,
                           val currencyCodeFrom: CurrencyCode,
                           val type: TradeType,
                           val price: BigDecimal,
                           val currencyCodeTo: CurrencyCode,
                           val feesAmount: BigDecimal,
                           val feesCurrencyCode: String,
                           val status: TradeStatus = TradeStatus.INITIAL
    ) : TransactionItemDomain(transactionId, date, amount, currencyCodeFrom) {
        enum class TradeType {
            UNKNOWN, BID, ASK
        }

        enum class TradeStatus {
            INITIAL,
            PENDING,
            PLACED,
            COMPLETED,
            FAILED,
            UNKNOWN
        }
    }
}
