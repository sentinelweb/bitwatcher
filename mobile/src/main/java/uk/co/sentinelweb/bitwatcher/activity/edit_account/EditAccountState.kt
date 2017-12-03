package uk.co.sentinelweb.bitwatcher.activity.edit_account

import uk.co.sentinelweb.bitwatcher.domain.AccountDomain
import uk.co.sentinelweb.bitwatcher.domain.AccountType

data class EditAccountState(
        val id: Long?
) {
    var domain: AccountDomain? = null
    var type: AccountType? = null
    var name: String? = null
}