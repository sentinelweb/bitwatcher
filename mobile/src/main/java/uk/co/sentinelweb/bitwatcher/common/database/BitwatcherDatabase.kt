package uk.co.sentinelweb.bitwatcher.common.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import uk.co.sentinelweb.bitwatcher.common.database.dao.AccountDao
import uk.co.sentinelweb.bitwatcher.common.database.dao.FullAccountDao
import uk.co.sentinelweb.bitwatcher.common.database.dao.PositionItemDao
import uk.co.sentinelweb.bitwatcher.common.database.dao.TickerDao
import uk.co.sentinelweb.bitwatcher.common.database.entities.AccountEntity
import uk.co.sentinelweb.bitwatcher.common.database.entities.PositionItemEntity
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity

// TODO add room.schemaLocation
@Database(entities = arrayOf(TickerEntity::class, AccountEntity::class, PositionItemEntity::class), version = 1)
abstract class BitwatcherDatabase : RoomDatabase() {
    abstract fun tickerDao(): TickerDao
    abstract fun accountDao(): AccountDao
    abstract fun positionItemDao(): PositionItemDao
    abstract fun fullAccountDao(): FullAccountDao
}