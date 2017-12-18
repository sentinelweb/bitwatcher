package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain

class TransactionStatusConverter {

    @TypeConverter
    fun toDb(code:TransactionDomain.TransactionStatus):String {
        return code.toString()
    }

    @TypeConverter
    fun fromDb(str:String):TransactionDomain.TransactionStatus {
        return TransactionDomain.TransactionStatus.valueOf(str)
    }
}