package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import java.math.BigDecimal

data class TradeInputState(
        var amount:BigDecimal,
        var price:BigDecimal,
        var linkedPrice:Boolean,
        var displayModel: TradeInputState.TradeInputDisplayModel
){
    data class TradeInputDisplayModel(
        val amount:String,
        val price:String,
        val linkedPrice:Boolean,
        val amountTrade:String,
        val amountHelp:String,
        val executeButtonLabel:String
    )
}