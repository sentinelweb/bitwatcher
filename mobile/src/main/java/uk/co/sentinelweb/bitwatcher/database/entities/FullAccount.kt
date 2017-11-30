package uk.co.sentinelweb.bitwatcher.database.entities

import android.arch.persistence.room.Relation
import android.arch.persistence.room.Transaction

class FullAccount {
    var id: Long = 0
    var name: String = ""
    @Relation(parentColumn = "id", entityColumn = "account_id", entity = PositionItem::class)
    var balances: List<PositionItem> = listOf()

    override fun toString(): String {
        return "fullaccount(${id} ${name} ${balances.size})"
    }
}