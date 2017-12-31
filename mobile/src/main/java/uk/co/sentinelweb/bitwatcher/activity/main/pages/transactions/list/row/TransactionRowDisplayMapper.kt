package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionItemModel
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactonRowState.DisplayModel.TradeDisplayModel
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row.TransactonRowState.DisplayModel.TransactionDisplayModel
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.ColourDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain
import java.text.SimpleDateFormat


class TransactionRowDisplayMapper {
    companion object {
        val DATE_FORMATTER = SimpleDateFormat.getDateTimeInstance() //SimpleDateFormat("dd/MM/yyyy @ hh:mm:ss")
    }
    fun map(item:TransactionItemModel): TransactonRowState.DisplayModel {
        return when (item.domain) {
            is TransactionDomain -> {
                TransactionDisplayModel(
                        item.domain.transactionId,
                        "${item.domain.amount.dp(2)} ${item.domain.currencyCode}",
                        DATE_FORMATTER.format(item.domain.date),
                        item.account.name,
                        ColourDomain.toInteger(item.account.colour),
                        when (item.domain.type) {
                            TransactionDomain.TransactionType.DEPOSIT -> R.drawable.ic_deposit_black_48dp
                            TransactionDomain.TransactionType.WITHDRAWL -> R.drawable.ic_withdraw_black_48dp
                            else -> R.drawable.ic_transaction_unknown_black_48dp
                        },
                        when (item.domain.type) {
                            TransactionDomain.TransactionType.DEPOSIT -> R.color.colorDeposit
                            TransactionDomain.TransactionType.WITHDRAWL -> R.color.colorWithdraw
                            else -> R.color.colorTransactionUnknown
                        },
                        item.domain.type.toString(),
                        item.domain.status.toString(),
                        "${item.domain.fee.dp(2)} ${item.domain.currencyCode}"

                )
            }
            is TradeDomain ->{
                TradeDisplayModel(
                        item.domain.transactionId,
                        "${item.domain.amount.dp(2)} ${item.domain.currencyCodeFrom}",
                        DATE_FORMATTER.format(item.domain.date),
                        item.account.name,
                        ColourDomain.toInteger(item.account.colour),
                        when (item.domain.type) {
                            TradeDomain.TradeType.ASK -> R.drawable.ic_sell_black_48dp
                            TradeDomain.TradeType.BID -> R.drawable.ic_buy_black_48dp
                            else -> R.drawable.ic_transaction_unknown_black_48dp
                        },
                        when (item.domain.type) {
                            TradeDomain.TradeType.ASK -> R.color.colorSell
                            TradeDomain.TradeType.BID -> R.color.colorBuy
                            else -> R.color.colorTransactionUnknown
                        },
                        if (item.domain.type == TradeDomain.TradeType.BID) "BUY" else "SELL" ,
                        "${item.domain.currencyCodeFrom}/${item.domain.currencyCodeTo}",
                        if (item.domain.status==TradeDomain.TradeStatus.COMPLETED) "" else item.domain.status.toString(),
                        "${item.domain.amount.multiply(item.domain.price).dp(5)}${item.domain.currencyCodeTo} @ ${item.domain.price.dp(4)}",
                        "${item.domain.feesAmount.dp(6)} ${item.domain.feesCurrencyCode}"
                        )
            }
        }
    }
}