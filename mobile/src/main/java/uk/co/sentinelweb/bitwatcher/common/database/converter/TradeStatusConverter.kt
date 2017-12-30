package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeStatus

class TradeStatusConverter {

    @TypeConverter
    fun toDb(code: TradeStatus):String {
        return code.toString()
    }

    @TypeConverter
    fun fromDb(str:String):TradeStatus {
        return TradeStatus.valueOf(str)
    }
}