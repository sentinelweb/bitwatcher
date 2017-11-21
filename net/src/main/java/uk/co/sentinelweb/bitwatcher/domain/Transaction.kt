package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal
import java.time.Instant

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

data class Transaction(
        val type: TransactionType,
        val date: Instant,
        val amount: BigDecimal,
        val currencyCode: String,
        val balance: BigDecimal,
        val description: String,
        val status: TransactionStatus,
        val fee: BigDecimal

)