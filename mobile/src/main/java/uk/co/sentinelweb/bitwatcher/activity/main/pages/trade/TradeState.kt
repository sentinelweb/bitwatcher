package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.TransactionItemModel
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyPair
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class TradeState(
        var accounts: List<AccountDomain>? = null,
        var accountNames: Array<String>? = null,
        var account: AccountDomain? = null,
        var markets: List<CurrencyPair>? = null,
        var market: CurrencyPair = CurrencyPair.NONE,
        var currentPrice: BigDecimal = ZERO,
        var selectedTrades: Set<TransactionItemModel>? = null
) {

    data class TradeDisplayModel(
            val accountInfo: String,
            val accountButtonLabel: String,
            val marketInfo: String,
            val marketButtonLabel: String,
            var showDeleteTradesButton:Boolean = false
    )




}