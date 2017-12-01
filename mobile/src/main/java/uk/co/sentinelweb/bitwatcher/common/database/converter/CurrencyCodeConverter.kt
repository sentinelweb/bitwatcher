package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode

class CurrencyCodeConverter {

    @TypeConverter
    fun toDb(code:CurrencyCode):String {
        return code.toString()
    }

    @TypeConverter
    fun fromDb(str:String):CurrencyCode {
        return CurrencyCode.lookup(str)!!
    }
}