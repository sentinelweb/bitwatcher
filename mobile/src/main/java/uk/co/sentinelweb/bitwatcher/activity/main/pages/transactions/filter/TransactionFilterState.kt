package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode

data class TransactionFilterState constructor(
        var accountId:Long? = null,
        var filterTypeList:MutableList<TransactionFilterDomain.TransactionFilterType> = mutableListOf(),
        var currency: CurrencyCode = CurrencyCode.NONE,
        var currencyBase: CurrencyCode = CurrencyCode.NONE
) {
    var accountList: List<AccountDomain>? = null
    var accountSelectionList: Array<String>? = null
}