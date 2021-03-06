package uk.co.sentinelweb.bitwatcher.common.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import android.arch.persistence.room.TypeConverters
import io.reactivex.Flowable
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.converter.AccountTypeConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.ColourDomainConverter
import uk.co.sentinelweb.bitwatcher.common.database.entities.FullAccountView
import uk.co.sentinelweb.domain.AccountType

@Dao
@TypeConverters(AccountTypeConverter::class, ColourDomainConverter::class)
abstract class FullAccountDao {
    @Query("SELECT id, name, type, colour from account")
    @Transaction
    abstract fun flowFullAccounts(): Flowable<List<FullAccountView>>

    @Query("SELECT id, name, type, colour from account")
    @Transaction
    abstract fun singleAllAccounts(): Single<List<FullAccountView>>

    @Query("SELECT id, name, type, colour from account")
    @Transaction
    abstract fun loadFullAccounts(): List<FullAccountView>

    @Query("SELECT id, name, type, colour from account WHERE type =:type")
    @Transaction
    abstract fun singleAccountsOfType(type:AccountType): Single<List<FullAccountView>>

    @Query("SELECT id, name, type, colour from account WHERE id = :id")
    @Transaction
    abstract fun singleFullAccount(id:Long): Single<FullAccountView>
}