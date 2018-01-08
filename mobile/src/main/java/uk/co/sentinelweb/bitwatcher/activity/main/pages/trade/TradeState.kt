package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode

data class TradeState(
        var account:AccountDomain,
        var fromCurrencyCode: CurrencyCode,
        var toCurrencyCode: CurrencyCode,
        var displayModel: TradeState.TradeDisplayModel
) {


    data class TradeDisplayModel(
            val accountInfo:String,
            val accountButtonLabel:String,
            val marketInfo:String,
            val marketButtonLabel:String

    )
}