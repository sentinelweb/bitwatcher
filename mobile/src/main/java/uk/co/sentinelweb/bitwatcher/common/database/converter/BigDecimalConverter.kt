package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import java.math.BigDecimal
import java.math.RoundingMode

class BigDecimalConverter {

    companion object {
        const val DEFUALT_SCALE = 6
    }

    @TypeConverter
    fun toDb(number:BigDecimal):String {
        return number.toString()
    }

    @TypeConverter
    fun fromDb(str:String):BigDecimal {
        try {
            return BigDecimal(str).setScale(DEFUALT_SCALE, RoundingMode.HALF_EVEN)
        } catch (nfex:NumberFormatException) {
            return BigDecimal.ZERO
        }
    }
}