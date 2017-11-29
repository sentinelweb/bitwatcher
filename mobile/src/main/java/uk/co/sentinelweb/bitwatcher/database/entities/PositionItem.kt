package uk.co.sentinelweb.bitwatcher.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity data class PositionItem constructor(
        @PrimaryKey val id: String,
        val currencyCode: String,
        val amount: String,
        @ColumnInfo(name = "account_id") val accountId: String
)