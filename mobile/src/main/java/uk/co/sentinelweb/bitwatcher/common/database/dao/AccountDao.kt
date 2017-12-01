package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.common.database.entities.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * From account")
    @Transaction
    fun getAllAccounts(): Flowable<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAccount(a: AccountEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAccount(a: AccountEntity)

    @Query("DELETE FROM account")
    fun deleteAll()
}