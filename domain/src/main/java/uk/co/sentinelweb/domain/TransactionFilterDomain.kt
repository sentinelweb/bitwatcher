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

    fun match(account: AccountDomain, transaction: TransactionItemDomain): Boolean {
        if (this.accountId != null && account.id != this.accountId) {
            return false
        }
        if (this.filterTypeList.isNotEmpty() && !this.filterTypeList.contains(mapType(transaction))) {
            return false
        }
        if (this.currencyFrom != CurrencyCode.NONE) {
            if (this.currencyTo != CurrencyCode.NONE && transaction is TransactionItemDomain.TradeDomain) { // match market
                if (transaction.currencyCodeTo != this.currencyTo || transaction.currencyCodeFrom != this.currencyFrom) {
                    return false
                }
            } else if (transaction.currencyCode != this.currencyFrom) { // match currencyFrom only
                return false
            }
        }

        if (!checkInRange(this.minAmount, this.maxAmount, transaction.amount)) {
            return false
        }
        if (!checkInRange(this.minDate, this.maxDate, transaction.date)) {
            return false
        }
        return true
    }

    private fun <T> checkInRange(min: Comparable<T>?, max: Comparable<T>?, value: T): Boolean {
        if (min != null || max != null) {
            if (min == null && max?.compareTo(value) == -1) {
                return false
            } else if (max == null && min?.compareTo(value) == 1) {
                return false
            } else if (min != null && max != null && !(min.compareTo(value) == -1 && max.compareTo(value) == 1)) {
                return false
            }
        }
        return true
    }

    private fun mapType(transaction: TransactionItemDomain): TransactionFilterDomain.TransactionFilterType {
        return when (transaction) {
            is TransactionItemDomain.TradeDomain -> when (transaction.type) {
                TransactionItemDomain.TradeDomain.TradeType.BID -> TransactionFilterDomain.TransactionFilterType.BUY
                TransactionItemDomain.TradeDomain.TradeType.ASK -> TransactionFilterDomain.TransactionFilterType.SELL
                else -> TransactionFilterDomain.TransactionFilterType.UNKNOWN
            }
            is TransactionItemDomain.TransactionDomain -> when (transaction.type) {
                TransactionItemDomain.TransactionDomain.TransactionType.DEPOSIT -> TransactionFilterDomain.TransactionFilterType.DEPOSIT
                TransactionItemDomain.TransactionDomain.TransactionType.WITHDRAWL -> TransactionFilterDomain.TransactionFilterType.WITHDRAW
                else -> TransactionFilterDomain.TransactionFilterType.UNKNOWN
            }
        }
    }
}