package uk.co.sentinelweb.bitwatcher.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = PositionItem::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("account_id"),
                onDelete = ForeignKey.CASCADE)))
data class Account constructor(
        @PrimaryKey val id: String,
        val name: String,
        @Relation(parentColumn = "id", entityColumn = "account_id", entity = PositionItem::class)
        val balances: List<PositionItem>
)