package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import uk.co.sentinelweb.bitwatcher.common.database.entities.PositionItemEntity

@Dao
interface PositionItemDao {

    @Query("SELECT * From position_item")
    fun getAllPositions(): Flowable<List<PositionItemEntity>>

    @Query("SELECT * From position_item WHERE account_id = :accountId")
    fun getAllPositionsForAccount(accountId: String): Flowable<List<PositionItemEntity>>

    @Query("SELECT id From position_item WHERE account_id = :accountId")
    fun getAllPositionsIdsForAccount(accountId: Long): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPositionItem(p: PositionItemEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePositionItem(p: PositionItemEntity)

    @Query("DELETE FROM position_item")
    fun deleteAll()

    @Query("DELETE FROM position_item WHERE id IN (:ids)")
    fun deleteIds(ids: List<Long>)


}