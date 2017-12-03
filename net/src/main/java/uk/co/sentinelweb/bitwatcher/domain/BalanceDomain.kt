package uk.co.sentinelweb.bitwatcher.domain

import java.io.Serializable
import java.math.BigDecimal

data class BalanceDomain(
        val id: Long?,
        val currency: CurrencyCode,
        val balance: BigDecimal,
        val available: BigDecimal,
        val reserved: BigDecimal): Serializable {
    companion object {
        private const val serialVersionUID: Long = 1234124523452562345
    }

    override fun toString(): String {
        return "${id} ${balance.setScale(2).toPlainString()} ${currency}"
    }
}