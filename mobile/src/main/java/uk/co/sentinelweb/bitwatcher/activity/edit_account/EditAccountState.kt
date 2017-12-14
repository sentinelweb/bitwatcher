package uk.co.sentinelweb.bitwatcher.activity.edit_account

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType

data class EditAccountState(
        val id: Long?
) {
    var domain: AccountDomain? = null
    var type: AccountType? = null
    var name: String? = null
}