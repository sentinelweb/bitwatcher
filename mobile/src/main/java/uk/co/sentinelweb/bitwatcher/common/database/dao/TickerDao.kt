package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.common.database.converter.BigDecimalConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.CurrencyCodeConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.DateConverter
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import java.math.BigDecimal
import java.util.*

@Dao
@TypeConverters(CurrencyCodeConverter::class, BigDecimalConverter::class, DateConverter::class)
interface TickerDao {
    @Query("SELECT * From ticker_data")
    fun flowAllTickers(): Flowable<List<TickerEntity>>

    @Query("SELECT count(*) From ticker_data")
    fun count(): Int

    @Query("Update ticker_data  " +
            "SET amount = :amount,  dateStamp = :dateStamp " +
            "WHERE currencyCode = :currencyCode AND baseCode = :baseCode " +
            ";"
    )
    fun updateTicker(currencyCode: CurrencyCode, baseCode: CurrencyCode, amount: BigDecimal, dateStamp: Date)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTicker(t: TickerEntity)

}