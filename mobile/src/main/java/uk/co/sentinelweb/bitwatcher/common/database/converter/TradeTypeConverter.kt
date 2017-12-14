package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import uk.co.sentinelweb.domain.TradeDomain

class TradeTypeConverter {

    @TypeConverter
    fun toDb(code:TradeDomain.TradeType):String {
        return code.toString()
    }

    @TypeConverter
    fun fromDb(str:String):TradeDomain.TradeType {
        return TradeDomain.TradeType.valueOf(str)
    }
}