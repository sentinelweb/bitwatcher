package uk.co.sentinelweb.bitwatcher.common.database


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import uk.co.sentinelweb.bitwatcher.common.database.dao.AccountDao
import uk.co.sentinelweb.bitwatcher.common.database.dao.FullAccountDao
import uk.co.sentinelweb.bitwatcher.common.database.dao.PositionItemDao
import uk.co.sentinelweb.bitwatcher.common.database.entities.AccountEntity
import uk.co.sentinelweb.bitwatcher.common.database.entities.PositionItemEntity

// TODO add room.schemaLocation
@Database(entities = arrayOf(AccountEntity::class, PositionItemEntity::class), version = 1)
abstract class BitwatcherMemoryDatabase : RoomDatabase() {
    abstract fun accountDao():AccountDao
    abstract fun positionItemDao():PositionItemDao
    abstract fun fullAccountDao():FullAccountDao
}