package uk.co.sentinelweb.domain

import java.math.BigDecimal
import java.util.*


data class TransactionFilterDomain constructor(
        val accountId:Long? = null,
        val name : String? = null,
        val filterTypeList:List<TransactionFilterType> = listOf(),
        val currencyFrom: CurrencyCode = CurrencyCode.NONE,
        val currencyTo: CurrencyCode = CurrencyCode.NONE,
        val saveDate:Date? = null,
        var minAmount: BigDecimal? = null,
        var maxAmount: BigDecimal? = null,
        var minDate:Date? = null,
        var maxDate:Date? = null
        ){

    enum class TransactionFilterType {
        BUY, SELL, DEPOSIT, WITHDRAW, UNKNOWN
    }
}