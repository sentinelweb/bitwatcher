package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import uk.co.sentinelweb.domain.CurrencyCode


data class TransactionFilterDomain constructor(
        val accountId:Long? = null,
        val filterTypeList:List<TransactionFilterType> = listOf(),
        val currency: CurrencyCode = CurrencyCode.NONE,
        val currencyBase: CurrencyCode = CurrencyCode.NONE
        ){

    enum class TransactionFilterType {
        BUY, SELL, DEPOSIT, WITHDRAW
    }
}