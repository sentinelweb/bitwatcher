package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.entities.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * From account")
    @Transaction
    fun flowAllAccounts(): Flowable<List<AccountEntity>>

    @Query("SELECT * From account")
    @Transaction
    fun singleAllAccounts(): Single<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAccount(a: AccountEntity):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAccount(a: AccountEntity)

    @Query("DELETE FROM account")
    fun deleteAll()

    @Query("DELETE FROM account WHERE id=:id")
    fun delete(id: Long)


}