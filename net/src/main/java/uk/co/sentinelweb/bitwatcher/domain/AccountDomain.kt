package uk.co.sentinelweb.bitwatcher.domain

import java.io.Serializable

data class AccountDomain(val id: Long?,
                         val name: String,
                         val type: AccountType,
                         val balances: List<BalanceDomain>) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 4563645846734523423
        val NONE = AccountDomain(null, "", AccountType.INITIAL, listOf())
    }

    override fun toString(): String {
        return "${id} ${name} ${type} ${balances}"
    }
}