package uk.co.sentinelweb.bitwatcher.common.database.migration

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

class Migration1To2:Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // transaction
        database.execSQL("CREATE TABLE IF NOT EXISTS `transaction` " +
                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "`account_id` INTEGER NOT NULL, " +
                "`type` TEXT NOT NULL, " +
                "`date` INTEGER NOT NULL, " +
                "`amount` TEXT NOT NULL, " +
                "`currencyCode` TEXT NOT NULL, " +
                "`balance` TEXT NOT NULL, " +
                "`description` TEXT NOT NULL, " +
                "`status` TEXT NOT NULL, " +
                "`fee` TEXT NOT NULL, " +
                "`feeCurrency` TEXT NOT NULL)")

        // transaction
        database.execSQL("CREATE TABLE IF NOT EXISTS `trade` " +
                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "`account_id` INTEGER NOT NULL, " +
                "`date` INTEGER NOT NULL, " +
                "`tid` TEXT NOT NULL, " +
                "`type` TEXT NOT NULL, " +
                "`price` TEXT NOT NULL, " +
                "`amount` TEXT NOT NULL, " +
                "`currencyCodeFrom` TEXT NOT NULL, " +
                "`currencyCodeTo` TEXT NOT NULL, " +
                "`feesAmount` TEXT NOT NULL, " +
                "`feesCurrency` TEXT NOT NULL)")
    }
}