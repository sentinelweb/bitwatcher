package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import uk.co.sentinelweb.domain.AccountType

class AccountTypeConverter {

    @TypeConverter
    fun toDb(code:AccountType):String {
        return code.toString()
    }

    @TypeConverter
    fun fromDb(str:String):AccountType {
        return AccountType.valueOf(str)
    }
}