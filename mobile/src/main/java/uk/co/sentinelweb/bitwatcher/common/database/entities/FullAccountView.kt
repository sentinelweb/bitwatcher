package uk.co.sentinelweb.bitwatcher.common.database.entities

import android.arch.persistence.room.Relation

class FullAccountView {
    var id: Long = 0
    var name: String = ""
    @Relation(parentColumn = "id", entityColumn = "account_id", entity = PositionItemEntity::class)
    var balances: List<PositionItemEntity> = listOf()

    override fun toString(): String {
        return "fullaccount(${id} ${name} ${balances.size})"
    }
}