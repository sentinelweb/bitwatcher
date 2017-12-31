package uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row

import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain
import uk.co.sentinelweb.domain.mappers.AccountTotalsMapper
import java.math.BigDecimal

class AccountDisplayDataMapper() {
    internal fun map(domain: AccountDomain, base: CurrencyCode, prices: Map<String, TickerDomain>): AccountRowState.DisplayData {
        val balancesText = StringBuilder()
        domain.balances.forEach({ balance ->
            if (balance.available > BigDecimal.ZERO) {
                balancesText
                        .append(if (balancesText.isEmpty()) "" else " \u00b7 ")
                        .append(balance.balance.dp(2))
                if (balance.balance != balance.available) {
                    balancesText
                            .append("/")
                            .append(balance.available.dp(2))
                }
                balancesText.append(" ")
                        .append(balance.currency.toString())
            }
        })
        val total = AccountTotalsMapper.getTotal(domain, base, prices, AccountTotalsMapper.BalanceType.BALANCE)
        return AccountRowState.DisplayData(
                domain.name,
                balancesText.toString(),
                total.dp(2) + " " + base.toString(),
                if (domain.type == AccountType.GHOST) R.color.colorGhostAcct else R.color.colorRealAcct)
    }

}
