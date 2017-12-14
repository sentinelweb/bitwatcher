package uk.co.sentinelweb.domain

import java.math.BigDecimal
import java.util.*

open class TransactionItem constructor(open val date: Date,
                                       open val amount: BigDecimal,
                                       open val currencyCode: CurrencyCode)
