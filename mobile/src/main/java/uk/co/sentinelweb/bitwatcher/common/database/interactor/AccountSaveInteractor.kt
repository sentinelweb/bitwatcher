package uk.co.sentinelweb.bitwatcher.common.database.interactor

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.PositionDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.domain.AccountDomain
import java.util.concurrent.Callable
import javax.inject.Inject

// TODO convert to proper Rx chain
// TODO try to setup foreign keys and cascading deletion for positionItems
class AccountSaveInteractor @Inject constructor(
        private val accountEntityMapper: AccountDomainToEntityMapper,
        private val positionEntityMapper: PositionDomainToEntityMapper,
        private val db: BitwatcherDatabase,
        private val accountDomainMapper: AccountEntityToDomainMapper

) {

    fun save(account: AccountDomain): Single<Boolean> {
//        val accountUpdate: Single<AccountEntity>
//        if (account.id == null) {
//            accountUpdate = db.accountDao()
//                    .insertAccountSingle(accountEntityMapper.map(account))
//                    .flatMap { id -> account.id = id;Single.just(accountEntityMapper.map(account)) }
//
//        } else {
//            accountUpdate = db.accountDao()
//                    .updateAccountCompletable(accountEntityMapper.map(account))
//                    .andThen(Single.just(account))
//        }
//        accountUpdate.flatMap { id -> Single.}

        return Single.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                val accountId:Long
                if (account.id == null) {
                    accountId = db.accountDao().insertAccount(accountEntityMapper.map(account))
                } else {
                    db.accountDao().updateAccount(accountEntityMapper.map(account))
                    accountId = account.id!!
                }
                val idDeleteList = (db.positionItemDao().getAllPositionsIdsForAccount(accountId)).toMutableList()
                account.balances.forEach({ balance ->
                    if (balance.id == null) {
                        db.positionItemDao().insertPositionItem(positionEntityMapper.map(balance, accountId))
                    } else {
                        db.positionItemDao().updatePositionItem(positionEntityMapper.map(balance, accountId))
                        idDeleteList.remove(balance.id!!)
                    }
                })
                db.positionItemDao().deleteIds(idDeleteList)
                return true
            }

        })
    }

    fun delete(account: AccountDomain): Single<Boolean> {
        return Single.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                val id = account.id
                if (id != null) {
                    db.accountDao().delete(id)
                    val posIdList = mutableListOf<Long>()
                    account.balances.forEach { position ->
                        val posId = position.id
                        if (posId != null) {
                            posIdList.add(posId)
                        }
                    }
                    db.positionItemDao().deleteIds(posIdList)
                }
                return true
            }
        })
    }
}