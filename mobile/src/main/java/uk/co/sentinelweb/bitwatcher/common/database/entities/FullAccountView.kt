package uk.co.sentinelweb.bitwatcher.common.database.entities

import android.arch.persistence.room.Relation
import uk.co.sentinelweb.bitwatcher.domain.AccountType

class FullAccountView {
    var id: Long = 0
    var name: String = ""
    var type: AccountType = AccountType.INITIAL
    @Relation(parentColumn = "id", entityColumn = "account_id", entity = PositionItemEntity::class)
    var balances: List<PositionItemEntity> = listOf()

    override fun toString(): String {
        return "fullaccount(${id} ${name} ${balances.size})"
    }
}