package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import android.arch.persistence.room.TypeConverters
import io.reactivex.Flowable
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.converter.AccountTypeConverter
import uk.co.sentinelweb.bitwatcher.common.database.entities.FullAccountView

@Dao
@TypeConverters(AccountTypeConverter::class)
abstract class FullAccountDao {
    @Query("SELECT id, name, type from account")
    @Transaction
    abstract fun flowFullAccounts(): Flowable<List<FullAccountView>>

    @Query("SELECT id, name, type from account")
    @Transaction
    abstract fun loadFullAccounts(): List<FullAccountView>

    @Query("SELECT id, name, type from account WHERE id = :id")
    @Transaction
    abstract fun singleFullAccount(id:Long): Single<FullAccountView>
}