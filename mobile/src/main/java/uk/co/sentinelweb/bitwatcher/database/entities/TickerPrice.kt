package uk.co.sentinelweb.bitwatcher.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "ticker_price" )
data class TickerPrice constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        var name: String
)