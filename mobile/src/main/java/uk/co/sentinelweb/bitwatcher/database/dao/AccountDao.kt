package uk.co.sentinelweb.bitwatcher.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.database.entities.Account

@Dao
interface AccountDao {

    @Query("SELECT * From account")
    @Transaction
    fun getAllAccounts(): Flowable<List<Account>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAccount(a: Account)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAccount(a: Account)

    @Query("DELETE FROM account")
    fun deleteAll()
}