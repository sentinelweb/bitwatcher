package uk.co.sentinelweb.bitwatcher.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.database.entities.PositionItemEntity

@Dao
interface PositionItemDao {

    @Query("SELECT * From position_item")
    fun getAllPositions(): Flowable<List<PositionItemEntity>>

    @Query("SELECT * From position_item WHERE account_id = :accountId")
    fun getAllPositionsForAccount(accountId: String): Flowable<List<PositionItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPositionItem(p: PositionItemEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePositionItem(p: PositionItemEntity)

    @Query("DELETE FROM position_item")
    fun deleteAll()
}