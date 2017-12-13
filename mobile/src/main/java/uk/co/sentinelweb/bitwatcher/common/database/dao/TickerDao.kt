package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
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

    @Query("SELECT * From ticker_data WHERE currencyCode=:code AND baseCode=:base")
    fun flowTicker(code:String, base:String): Flowable<TickerEntity>

    @Query("SELECT * From ticker_data WHERE currencyCode=:code AND baseCode=:base")
    fun singleTicker(code:String, base:String): Single<TickerEntity>

    @Query("SELECT * From ticker_data WHERE currencyCode=:code AND baseCode=:base")
    fun loadTicker(code:CurrencyCode, base:CurrencyCode): TickerEntity?

    @Query("SELECT count(*) From ticker_data")
    fun count(): Int

    @Query("Update ticker_data  " +
            "SET amount = :amount,  dateStamp = :dateStamp " +
            "WHERE currencyCode = :currencyCode AND baseCode = :baseCode " +
            ";"
    )
    fun updateTicker(currencyCode: CurrencyCode, baseCode: CurrencyCode, amount: BigDecimal, dateStamp: Date)

    @Query("Update ticker_data  " +
            "SET name = :name " +
            "WHERE currencyCode = :currencyCode AND baseCode = :baseCode " +
            ";"
    )
    fun updateName(currencyCode: CurrencyCode, baseCode: CurrencyCode, name: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTicker(t: TickerEntity)

}