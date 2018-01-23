package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.converter.BigDecimalConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.CurrencyCodeConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.DateConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.TradeTypeConverter
import uk.co.sentinelweb.bitwatcher.common.database.entities.TradeEntity

@Dao
@TypeConverters(CurrencyCodeConverter::class, BigDecimalConverter::class, DateConverter::class, TradeTypeConverter::class)
interface TradeDao {
    @Query("SELECT * From trade")
    fun singleAllTrades(): Single<List<TradeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrade(t: TradeEntity):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTrade(t: TradeEntity)

    @Query("SELECT * From trade where account_id=:accountId and tid=:transactionId")
    fun checkTradeInDb(accountId: Long, transactionId: String): TradeEntity?



}