package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import uk.co.sentinelweb.domain.TransactionItemDomain

data class TransactonRowState constructor(var transaction: TransactionItemDomain? = null) {
    var model: DisplayModel? = null

    sealed class DisplayModel constructor(open val id: String,open val amount: String, open val date: String) {

        data class TradeDisplayModel(
                override val id: String,
                override val amount: String,
                override val date: String,
                @DrawableRes val icon: Int,
                @ColorRes val iconColor: Int,
                val type: String,
                val market: String,
                val quantityAndRate: String,
                val feeAmount: String
        ) : DisplayModel(id, amount, date)

        data class TransactionDisplayModel(
                override val id: String,
                override val amount: String,
                override val date: String,
                @DrawableRes val icon: Int,
                @ColorRes val iconColor: Int,
                val type: String,
                val status: String,
                val feeAmount: String
        ) : DisplayModel(id, amount, date)
    }
}