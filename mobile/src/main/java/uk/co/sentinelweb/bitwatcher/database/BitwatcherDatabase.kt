package uk.co.sentinelweb.bitwatcher.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import uk.co.sentinelweb.bitwatcher.database.dao.TickerDao
import uk.co.sentinelweb.bitwatcher.database.entities.TickerEntity

// TODO add room.schemaLocation
@Database(entities = arrayOf(TickerEntity::class), version = 1)
abstract class BitwatcherDatabase : RoomDatabase() {
    abstract fun tickerDao(): TickerDao
}