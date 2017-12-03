package uk.co.sentinelweb.bitwatcher.activity.edit_account.view

import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import java.math.BigDecimal

class BalanceItemState() {
    var id: Long? = null
    var amount: BigDecimal? = null
    var code:CurrencyCode? = null
}
