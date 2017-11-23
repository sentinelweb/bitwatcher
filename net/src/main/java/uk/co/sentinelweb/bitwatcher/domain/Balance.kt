package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal

data class Balance(
        val currency:CurrencyCode,
        val balance: BigDecimal,
        val available:BigDecimal,
        val reserved:BigDecimal)