package uk.co.sentinelweb.bitwatcher.common.database.migration

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

class Migration3to4 :Migration(3,4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE trade ADD COLUMN status TEXT NOT NULL default 'INITIAL'")
    }
}