package uk.co.sentinelweb.bitwatcher.common.database.migration

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

class Migration2to3 :Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE account "
                + " ADD COLUMN colour INTEGER")
    }
}