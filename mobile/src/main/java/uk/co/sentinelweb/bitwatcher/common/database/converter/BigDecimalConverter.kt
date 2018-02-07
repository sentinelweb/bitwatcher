package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import uk.co.sentinelweb.domain.mc
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
            return BigDecimal(str, mc)
        } catch (nfex:NumberFormatException) {
            return BigDecimal.ZERO
        }
    }
}