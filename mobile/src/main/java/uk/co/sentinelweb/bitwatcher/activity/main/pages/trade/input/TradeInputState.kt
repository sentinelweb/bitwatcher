package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import android.support.annotation.ColorRes
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType.UNKNOWN
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class TradeInputState(
        var amount:BigDecimal = ZERO,
        var price:BigDecimal = ZERO,
        var linkedPrice:Boolean = true,
        var account: AccountDomain? = null,
        var market: CurrencyPair = CurrencyPair.NONE,
        var tradeType: TradeType = UNKNOWN,
        var amountCurrency: CurrencyCode = CurrencyCode.NONE,
        var currentPrice: BigDecimal = ZERO,
        var otherAmount: BigDecimal = ZERO
){
    data class TradeInputDisplayModel(
        val amount:String,
        val price:String,
        val linkedPrice:Boolean,
        val amountTrade:String,
        val amountHelp:String,
        val executeButtonLabel:String,
        val executeButtonEnabled:Boolean,
        @ColorRes val executeButtonColor:Int
    )

    enum class Field {
        AMOUNT, PRICE
    }

}