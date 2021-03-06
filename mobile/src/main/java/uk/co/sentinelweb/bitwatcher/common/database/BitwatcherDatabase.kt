package uk.co.sentinelweb.bitwatcher.common.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import uk.co.sentinelweb.bitwatcher.common.database.dao.*
import uk.co.sentinelweb.bitwatcher.common.database.entities.*

@Database(entities = arrayOf(TickerEntity::class, AccountEntity::class, PositionItemEntity::class, TradeEntity::class, TransactionEntity::class), version = 3)
abstract class BitwatcherDatabase : RoomDatabase() {
    abstract fun tickerDao(): TickerDao
    abstract fun accountDao(): AccountDao
    abstract fun positionItemDao(): PositionItemDao
    abstract fun fullAccountDao(): FullAccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun tradeDao(): TradeDao
}