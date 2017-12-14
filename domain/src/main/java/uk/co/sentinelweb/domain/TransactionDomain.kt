package uk.co.sentinelweb.domain

import java.math.BigDecimal
import java.util.*

data class TransactionDomain(
        val type: TransactionType,
        val date: Date,
        val amount: BigDecimal,
        val currencyCode: CurrencyCode,
        val balance: BigDecimal,
        val description: String,
        val status: TransactionStatus,
        val fee: BigDecimal ) {

    enum class TransactionType {
        DEPOSIT,
        WITHDRAWL
    }

    enum class TransactionStatus {
        PROCESSING,
        COMPLETE,
        CANCELLED,
        FAILED,
        UNKNOWN
    }
}

