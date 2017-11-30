package uk.co.sentinelweb.bitwatcher.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.database.entities.FullAccount

@Dao
abstract class FullAccountDao {
    @Query("SELECT id, name from Account")
    abstract fun flowFullAccounts(): Flowable<List<FullAccount>>

    @Query("SELECT id, name from Account")
    abstract fun loadFullAccounts(): List<FullAccount>
}