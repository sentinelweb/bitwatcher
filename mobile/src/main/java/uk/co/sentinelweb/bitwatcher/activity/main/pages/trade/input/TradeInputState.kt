package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyPair
import java.math.BigDecimal

data class TradeInputState(
        var amount:BigDecimal = BigDecimal.ZERO,
        var price:BigDecimal = BigDecimal.ZERO,
        var linkedPrice:Boolean = true,
        var account: AccountDomain? = null,
        var market: CurrencyPair = CurrencyPair.NONE
){
    data class TradeInputDisplayModel(
        val amount:String,
        val price:String,
        val linkedPrice:Boolean,
        val amountTrade:String,
        val amountHelp:String,
        val executeButtonLabel:String,
        val executeButtonEnabled:Boolean
    )


}