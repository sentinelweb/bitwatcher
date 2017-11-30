package uk.co.sentinelweb.bitwatcher.database


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import uk.co.sentinelweb.bitwatcher.database.dao.AccountDao
import uk.co.sentinelweb.bitwatcher.database.dao.FullAccountDao
import uk.co.sentinelweb.bitwatcher.database.dao.PositionItemDao
import uk.co.sentinelweb.bitwatcher.database.entities.Account
import uk.co.sentinelweb.bitwatcher.database.entities.PositionItem
import uk.co.sentinelweb.bitwatcher.database.entities.TickerPrice

// TODO add room.schemaLocation
@Database(entities = arrayOf(Account::class, PositionItem::class, TickerPrice::class), version = 1)
abstract class BitwatcherDatabase: RoomDatabase() {
    abstract fun accountDao():AccountDao
    abstract fun positionItemDao():PositionItemDao
    abstract fun fullAccountDao():FullAccountDao
}