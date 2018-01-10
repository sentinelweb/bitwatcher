package uk.co.sentinelweb.domain

import java.math.BigDecimal

data class AmountDomain constructor(
        val amount:BigDecimal,
        val currencyCode: CurrencyCode
)
