package uk.co.sentinelweb.domain

import java.math.BigDecimal
import java.util.*

data class TransactionDomain(
        val type: TransactionType,
        override val date: Date,
        override val amount: BigDecimal,
        override val currencyCode: CurrencyCode,
        val balance: BigDecimal,
        val description: String,
        val status: TransactionStatus,
        val fee: BigDecimal ) : TransactionItem(date, amount, currencyCode){

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

