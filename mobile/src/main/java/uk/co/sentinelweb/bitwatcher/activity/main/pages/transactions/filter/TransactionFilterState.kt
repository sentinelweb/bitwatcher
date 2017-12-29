package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionFilterDomain
import java.math.BigDecimal
import java.util.*

data class TransactionFilterState constructor(
        var accountId:Long? = null,
        var filterName : String? = null,
        var filterTypeList:MutableList<TransactionFilterDomain.TransactionFilterType> = mutableListOf(),
        var currencyFrom: CurrencyCode = CurrencyCode.NONE,
        var currencyTo: CurrencyCode = CurrencyCode.NONE,
        var saveDate: Date? = null,
        var minAmount:BigDecimal? = null,
        var maxAmount:BigDecimal? = null,
        var minDate:Date? = null,
        var maxDate:Date? = null
) {
    var accountList: List<AccountDomain>? = null
    var accountSelectionList: Array<String>? = null
}