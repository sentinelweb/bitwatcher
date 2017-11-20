package uk.co.sentinelweb.bitwatcher.domain

import java.math.BigDecimal

data class Balance(
        val currency:String,
        val balance: BigDecimal,
        val available:BigDecimal,
        val reserved:BigDecimal)