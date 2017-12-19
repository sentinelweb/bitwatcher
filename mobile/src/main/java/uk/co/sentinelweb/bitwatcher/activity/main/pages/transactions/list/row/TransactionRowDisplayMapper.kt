package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactonRowState.DisplayModel.TradeDisplayModel
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactonRowState.DisplayModel.TransactionDisplayModel
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain
import uk.co.sentinelweb.domain.TransactionItemDomain
import java.text.SimpleDateFormat


class TransactionRowDisplayMapper {
    companion object {
        val DATE_FORMATTER = SimpleDateFormat.getDateTimeInstance();//SimpleDateFormat("dd/MM/yyyy @ hh:mm:ss")
    }
    fun map(item:TransactionItemDomain): TransactonRowState.DisplayModel {
        return when (item) {
            is TransactionDomain -> {
                TransactionDisplayModel(
                        item.transactionId,
                        "${item.amount.dp(2)} ${item.currencyCode}",
                        DATE_FORMATTER.format(item.date),
                        when (item.type) {
                            TransactionDomain.TransactionType.DEPOSIT -> R.drawable.ic_deposit_black_48dp
                            TransactionDomain.TransactionType.WITHDRAWL -> R.drawable.ic_withdraw_black_48dp
                            else -> R.drawable.ic_transaction_unknown_black_48dp
                        },
                        when (item.type) {
                            TransactionDomain.TransactionType.DEPOSIT -> R.color.colorDeposit
                            TransactionDomain.TransactionType.WITHDRAWL -> R.color.colorWithdraw
                            else -> R.color.colorTransactionUnknown
                        },
                        item.type.toString(),
                        item.status.toString(),
                        "${item.fee.dp(2)} ${item.currencyCode}"

                )
            }
            is TradeDomain ->{
                TradeDisplayModel(
                        item.transactionId,
                        "${item.amount.dp(2)} ${item.currencyCodeFrom}",
                        DATE_FORMATTER.format(item.date),
                        when (item.type) {
                            TradeDomain.TradeType.ASK -> R.drawable.ic_sell_black_48dp
                            TradeDomain.TradeType.BID -> R.drawable.ic_buy_black_48dp
                            else -> R.drawable.ic_transaction_unknown_black_48dp
                        },
                        when (item.type) {
                            TradeDomain.TradeType.ASK -> R.color.colorSell
                            TradeDomain.TradeType.BID -> R.color.colorBuy
                            else -> R.color.colorTransactionUnknown
                        },
                        item.type.toString(),
                        "${item.currencyCodeFrom}/${item.currencyCodeTo}",
                        "${item.amount.multiply(item.price).dp(5)}${item.currencyCodeTo} @ ${item.price.dp(4)}",
                        "${item.feesAmount.dp(6)} ${item.feesCurrencyCode}"
                        )
            }
        }
    }
}