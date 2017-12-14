package uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row

import android.support.annotation.ColorRes
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode

data class AccountRowState(
        var domain:AccountDomain,
        var checked:Boolean,
        var totalCurrency: CurrencyCode,
        var displayData: DisplayData? = null,
        var visible: Boolean = true
) {
    data class DisplayData(val nameText:String,
                           val balancesText:String,
                           val totalText:String,
                           @ColorRes val nameTextColor:Int
                           )



}
