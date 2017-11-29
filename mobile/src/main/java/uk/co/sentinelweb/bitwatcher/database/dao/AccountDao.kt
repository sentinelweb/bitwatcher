package uk.co.sentinelweb.bitwatcher.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.database.entities.Account

@Dao
interface AccountDao {

    @Query("SELECT * From Account")
    fun getAllAccounts(): Flowable<List<Account>>


}