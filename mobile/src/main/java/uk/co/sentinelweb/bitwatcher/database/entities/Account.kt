package uk.co.sentinelweb.bitwatcher.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "account")
data class Account constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long?,// TODO better way to autoincrement?
        val name: String
)

//,
//        foreignKeys = arrayOf(
//        ForeignKey(entity = PositionItem::class,
//                parentColumns = arrayOf("id"),
//                childColumns = arrayOf("account_id"),
//                onDelete = ForeignKey.CASCADE))