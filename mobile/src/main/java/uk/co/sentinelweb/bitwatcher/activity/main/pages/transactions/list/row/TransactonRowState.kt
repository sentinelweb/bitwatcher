package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionItemModel

data class TransactonRowState constructor(
         var transaction: TransactionItemModel? = null,
         var selected:Boolean = false
) {

    sealed class DisplayModel constructor(open val id: String, open val amount: String, open val date: String, open val accountName: String, open val accountColor: Int, @ColorRes open val backgroundColor: Int) {

        data class TradeDisplayModel(
                override val id: String,
                override val amount: String,
                override val date: String,
                override val accountName: String,
                override val accountColor: Int,
                @ColorRes override val backgroundColor: Int,
                @DrawableRes val icon: Int,
                @ColorRes val iconColor: Int,
                val type: String,
                val market: String,
                val status: String,
                val quantityAndRate: String,
                val feeAmount: String

        ) : DisplayModel(id, amount, date, accountName, accountColor, backgroundColor)

        data class TransactionDisplayModel(
                override val id: String,
                override val amount: String,
                override val date: String,
                override val accountName: String,
                override val accountColor: Int,
                @ColorRes override val backgroundColor: Int,
                @DrawableRes val icon: Int,
                @ColorRes val iconColor: Int,
                val type: String,
                val status: String,
                val feeAmount: String
        ) : DisplayModel(id, amount, date, accountName, accountColor, backgroundColor)
    }
}