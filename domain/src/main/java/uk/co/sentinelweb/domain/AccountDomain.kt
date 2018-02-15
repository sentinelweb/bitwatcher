package uk.co.sentinelweb.domain

import java.io.Serializable

//@kotlinx.serialization.Serializable
data class AccountDomain(val id: Long?,
                         val name: String,
                         val type: AccountType,
                         val balances: List<BalanceDomain>,
                         val tranasactions: List<TransactionItemDomain> = listOf(),
                         val colour: ColourDomain = ColourDomain.TRANSPARENT)  : Serializable{

    companion object {
        private const val serialVersionUID: Long = 4563645846734523423
        val NONE = AccountDomain(null, "", AccountType.INITIAL, listOf())
    }

    override fun toString(): String {
        return "${id} ${name} ${type} ${balances}"
    }
}