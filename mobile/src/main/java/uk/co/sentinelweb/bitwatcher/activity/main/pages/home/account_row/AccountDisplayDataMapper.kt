package uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row

import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.bitwatcher.common.mapper.AccountBalancesMapper
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain
import uk.co.sentinelweb.domain.mappers.AccountTotalsMapper

class AccountDisplayDataMapper constructor(
        private val balancesMapper: AccountBalancesMapper = AccountBalancesMapper()
) {

    internal fun map(domain: AccountDomain, base: CurrencyCode, prices: Map<String, TickerDomain>): AccountRowState.DisplayData {
        val balancesText = balancesMapper.mapBalances(domain)
        val total = AccountTotalsMapper.getTotal(domain, base, prices, AccountTotalsMapper.BalanceType.BALANCE)
        return AccountRowState.DisplayData(
                domain.name,
                balancesText,
                total.dp(2) + " " + base.toString(),
                if (domain.type == AccountType.GHOST) R.color.colorGhostAcct else R.color.colorRealAcct)
    }



}
