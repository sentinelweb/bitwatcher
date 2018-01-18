package uk.co.sentinelweb.bitwatcher.common.mapper

import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.AccountDomain
import java.math.BigDecimal
import javax.inject.Inject

class AccountBalancesMapper @Inject constructor() {

    fun mapBalances(domain: AccountDomain?): String {
        val balancesText = StringBuilder()
        if (domain != null) {
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
        }
        return balancesText.toString()
    }
}