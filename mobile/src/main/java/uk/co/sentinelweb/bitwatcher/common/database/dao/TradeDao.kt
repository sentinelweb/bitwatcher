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

    @Query("SELECT * From trade where account_id=:accountId and tid=:transactionId")
    fun checkTradeInDb(accountId: Long, transactionId: String): TradeEntity?

    @Query("SELECT * From trade where account_id=:acctId and status NOT IN :excludeStatuses")//and status IN :includeStatuses
    fun singleTradesForAccountWithStatus(
            acctId: Long,
            //includeStatuses: List<TradeStatus>,
            excludeStatuses: List<TradeStatus>): Single<List<TradeEntity>>

    @Query("SELECT * From trade where account_id=:acctId and status = :status")
    fun singleTradesForAccountWithStatus(
            acctId: Long,
            status: TradeStatus): Single<List<TradeEntity>>


}