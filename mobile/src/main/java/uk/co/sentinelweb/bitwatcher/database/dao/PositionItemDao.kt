package uk.co.sentinelweb.bitwatcher.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.database.entities.PositionItem

@Dao
interface PositionItemDao {

    @Query("SELECT * From position_item")
    fun getAllPositions(): Flowable<List<PositionItem>>

    @Query("SELECT * From position_item WHERE account_id = :accountId")
    fun getAllPositionsForAccount(accountId: String): Flowable<List<PositionItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPositionItem(p: PositionItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePositionItem(p: PositionItem)

    @Query("DELETE FROM position_item")
    fun deleteAll()
}