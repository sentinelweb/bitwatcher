package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.common.database.converter.*
import uk.co.sentinelweb.bitwatcher.common.database.entities.TransactionEntity

@Dao
@TypeConverters(CurrencyCodeConverter::class, BigDecimalConverter::class, DateConverter::class,TransactionTypeConverter::class, TransactionStatusConverter::class)
interface TransactionDao {
    @Query("SELECT * From transaction")
    fun flowAllTransactions(): Flowable<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(t: TransactionEntity)

}