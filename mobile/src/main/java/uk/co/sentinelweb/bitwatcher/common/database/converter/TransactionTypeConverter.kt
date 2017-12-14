package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import uk.co.sentinelweb.domain.TransactionDomain

class TransactionTypeConverter {

    @TypeConverter
    fun toDb(code:TransactionDomain.TransactionType):String {
        return code.toString()
    }

    @TypeConverter
    fun fromDb(str:String):TransactionDomain.TransactionType {
        return TransactionDomain.TransactionType.valueOf(str)
    }
}