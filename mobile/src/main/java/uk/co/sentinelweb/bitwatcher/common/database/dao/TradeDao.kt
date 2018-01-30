package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.converter.*
import uk.co.sentinelweb.bitwatcher.common.database.entities.TradeEntity
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeStatus

@Dao
@TypeConverters(CurrencyCodeConverter::class, BigDecimalConverter::class, DateConverter::class, TradeTypeConverter::class, TradeStatusConverter::class)
interface TradeDao {

    @Query("SELECT * From trade")
    fun singleAllTrades(): Single<List<TradeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrade(t: TradeEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTrade(t: TradeEntity)

    @Delete()
    fun deleteTrade(t: TradeEntity): Int

    @Query("DELETE From trade where account_id=:accountId and tid=:transactionId")
    fun deleteTrade(accountId: Long, transactionId: String): Int

    @Query("SELECT * From trade where account_id=:accountId and tid=:transactionId")
    fun checkTradeInDb(accountId: Long, transactionId: String): TradeEntity?

    // FIXME room not processing kotlin lists
    @Query("SELECT * From trade where account_id=:acctId and status NOT IN (:excludeStatuses)")//and status IN :includeStatuses
    fun singleTradesForAccountWithOutStatuses(
            acctId: Long,
            @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") excludeStatuses: java.util.List<TradeStatus>
    ): Single<List<TradeEntity>>

    // FIXME room not processing kotlin lists
    @Query("SELECT * From trade where account_id=:acctId and status IN (:includeStatuses)")//and status IN :includeStatuses
    fun singleTradesForAccountWithStatuses(
            acctId: Long,
            @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") includeStatuses: java.util.List<TradeStatus>
    ): Single<List<TradeEntity>>

    @Query("SELECT * From trade where account_id=:acctId and status = :status")
    fun singleTradesForAccountWithStatus(
            acctId: Long,
            status: TradeStatus): Single<List<TradeEntity>>


}