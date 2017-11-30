package uk.co.sentinelweb.bitwatcher.database.converter

import android.arch.persistence.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {

    @TypeConverter
    fun toDb(number:BigDecimal):String {
        return number.toString()
    }

    @TypeConverter
    fun fromDb(str:String):BigDecimal {
        try {
            return BigDecimal(str)
        } catch (nfex:NumberFormatException) {
            return BigDecimal.ZERO
        }
    }
}