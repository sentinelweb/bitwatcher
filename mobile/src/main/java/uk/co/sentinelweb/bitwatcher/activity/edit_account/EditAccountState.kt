package uk.co.sentinelweb.bitwatcher.activity.edit_account

import uk.co.sentinelweb.bitwatcher.domain.AccountType
import uk.co.sentinelweb.bitwatcher.domain.AccoutDomain
import uk.co.sentinelweb.bitwatcher.domain.BalanceDomain

data class EditAccountState(
        val id: Long?
) {
    var domain: AccoutDomain? = null
    var type: AccountType? = null
    var name: String? = null
    var balances: List<BalanceDomain>? = null
}