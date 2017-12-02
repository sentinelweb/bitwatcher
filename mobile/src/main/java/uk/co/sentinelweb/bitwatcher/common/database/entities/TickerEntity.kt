package uk.co.sentinelweb.bitwatcher.common.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import uk.co.sentinelweb.bitwatcher.common.database.converter.BigDecimalConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.CurrencyCodeConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.DateConverter
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "ticker_data")
@TypeConverters(CurrencyCodeConverter::class, BigDecimalConverter::class, DateConverter::class)
data class TickerEntity constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val name: String,
        val currencyCode: CurrencyCode,
        val baseCode: CurrencyCode,
        val amount: BigDecimal,
        val dateStamp: Date
)