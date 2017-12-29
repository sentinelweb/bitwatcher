package uk.co.sentinelweb.domain.extensions

import uk.co.sentinelweb.domain.*

fun TickerDomain.getPairKey(): String = CurrencyPair.getKey(this.currencyCode, this.baseCurrencyCode)

fun TransactionFilterDomain.satisfies(account: AccountDomain, transaction: TransactionItemDomain): Boolean {
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
