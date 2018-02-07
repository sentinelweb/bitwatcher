package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Single
import org.reactivestreams.Publisher
import uk.co.sentinelweb.bitwatcher.common.database.converter.BigDecimalConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.CurrencyCodeConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.DateConverter
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain
import java.math.BigDecimal
import java.util.*

@Dao
@TypeConverters(CurrencyCodeConverter::class, BigDecimalConverter::class, DateConverter::class)
interface TickerDao {

    @Query("SELECT * From ticker_data WHERE currencyCode=:code AND baseCode=:base AND name='current'")
    fun flowTicker(code:CurrencyCode, base:CurrencyCode): Publisher<TickerEntity>

    @Query("SELECT * From ticker_data WHERE currencyCode=:code AND baseCode=:base AND name='current'")
    fun singleTicker(code:CurrencyCode, base:CurrencyCode): Single<TickerEntity>

    @Query("SELECT * From ticker_data WHERE currencyCode=:code AND baseCode=:base AND name=:name")
    fun singleTickerNamed(code:CurrencyCode, base:CurrencyCode, name:String): Single<TickerEntity>

    @Query("SELECT * From ticker_data WHERE currencyCode=:code AND baseCode=:base AND name='current'")
    fun loadTicker(code:CurrencyCode, base:CurrencyCode): TickerEntity?

    @Query("SELECT * From ticker_data WHERE currencyCode=:code AND baseCode=:base AND name=:name")
    fun loadTickerNamed(code:CurrencyCode, base:CurrencyCode, name:String): TickerEntity?

    @Query("SELECT id From ticker_data WHERE currencyCode=:code AND baseCode=:base AND name=:name")
    fun getTickerIdNamed(code:CurrencyCode, base:CurrencyCode, name:String): Long?

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