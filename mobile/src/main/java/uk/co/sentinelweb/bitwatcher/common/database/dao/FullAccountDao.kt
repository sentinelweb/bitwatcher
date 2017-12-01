package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.common.database.entities.FullAccountView

@Dao
abstract class FullAccountDao {
    @Query("SELECT id, name from account")
    @Transaction
    abstract fun flowFullAccounts(): Flowable<List<FullAccountView>>

    @Query("SELECT id, name from account")
    @Transaction
    abstract fun loadFullAccounts(): List<FullAccountView>
}