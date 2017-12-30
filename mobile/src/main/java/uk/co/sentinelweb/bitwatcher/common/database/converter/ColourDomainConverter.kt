package uk.co.sentinelweb.bitwatcher.common.database.converter

import android.arch.persistence.room.TypeConverter
import uk.co.sentinelweb.domain.ColourDomain

class ColourDomainConverter {

    @TypeConverter
    fun toDb(colour: ColourDomain):Int {
        return ColourDomain.toInteger(colour)
    }

    @TypeConverter
    fun fromDb(int:Int): ColourDomain {
        return ColourDomain.fromInteger(int)
    }
}