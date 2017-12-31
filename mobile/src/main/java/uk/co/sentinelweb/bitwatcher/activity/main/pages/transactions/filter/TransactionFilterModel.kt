package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionFilterDomain


data class TransactionFilterModel constructor(
        val accountName: String? = null,
        val name: String? = null,
        val filterTypeList: List<TransactionFilterDomain.TransactionFilterType> = listOf(),
        val currencyFrom: CurrencyCode = CurrencyCode.NONE,
        val currencyTo: CurrencyCode = CurrencyCode.NONE,
        val saveDate: String? = null,
        val minAmount: String? = null,
        val maxAmount: String? = null,
        val minDate: String = "*",
        val maxDate: String = "*",
        val deleteVisible: Boolean = false,
        val amountEnabled: Boolean = false
)