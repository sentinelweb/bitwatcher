package uk.co.sentinelweb.bitwatcher.database.entities

import android.arch.persistence.room.Relation

class FullAccount {
    var id: String = ""
    var name: String = ""
    @Relation(parentColumn = "id", entityColumn = "account_id", entity = PositionItem::class)
    var balances: List<PositionItem> = listOf()

    override fun toString(): String {
        return "fullaccount(${id} ${name} ${balances.size})"
    }
}