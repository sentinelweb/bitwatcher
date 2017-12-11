package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.converter.*
import uk.co.sentinelweb.bitwatcher.common.database.entities.TransactionEntity

@Dao
@TypeConverters(CurrencyCodeConverter::class, BigDecimalConverter::class, DateConverter::class, TransactionTypeConverter::class, TransactionStatusConverter::class)
interface TransactionDao {

    @Query("SELECT * from `transaction`")
    fun singleAllTransactions(): Single<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(t: TransactionEntity)

}