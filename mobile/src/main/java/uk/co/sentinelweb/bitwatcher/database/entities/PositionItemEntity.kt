package uk.co.sentinelweb.bitwatcher.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import uk.co.sentinelweb.bitwatcher.database.converter.BigDecimalConverter
import uk.co.sentinelweb.bitwatcher.database.converter.CurrencyCodeConverter
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import java.math.BigDecimal

@Entity(tableName = "position_item")
@TypeConverters(CurrencyCodeConverter::class, BigDecimalConverter::class)
data class PositionItemEntity constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long?,
        val currencyCode: CurrencyCode,
        val amount: BigDecimal,
        @ColumnInfo(name = "account_id")
        val accountId: Long
)