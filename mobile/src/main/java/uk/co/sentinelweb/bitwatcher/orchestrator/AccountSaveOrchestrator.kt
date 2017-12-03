package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherMemoryDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.PositionDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.domain.AccountDomain
import java.util.concurrent.Callable
import javax.inject.Inject

// TODO convert to proper Rx chain
class AccountSaveOrchestrator @Inject constructor(
        private val accountEntityMapper: AccountDomainToEntityMapper,
        private val positionEntityMapper: PositionDomainToEntityMapper,
        private val dbMem: BitwatcherMemoryDatabase,
        private val accountDomainMapper: AccountEntityToDomainMapper

) {

    fun save(account: AccountDomain): Single<Boolean> {
//        val accountUpdate: Single<AccountEntity>
//        if (account.id == null) {
//            accountUpdate = dbMem.accountDao()
//                    .insertAccountSingle(accountEntityMapper.map(account))
//                    .flatMap { id -> account.id = id;Single.just(accountEntityMapper.map(account)) }
//
//        } else {
//            accountUpdate = dbMem.accountDao()
//                    .updateAccountCompletable(accountEntityMapper.map(account))
//                    .andThen(Single.just(account))
//        }
//        accountUpdate.flatMap { id -> Single.}

        return Single.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                val accountId:Long
                if (account.id == null) {
                    accountId = dbMem.accountDao().insertAccount(accountEntityMapper.map(account))
                } else {
                    dbMem.accountDao().updateAccount(accountEntityMapper.map(account))
                    accountId = account.id!!
                }
                val idDeleteList = (dbMem.positionItemDao().getAllPositionsIdsForAccount(accountId)).toMutableList()
                account.balances.forEach({ balance ->
                    if (balance.id == null) {
                        dbMem.positionItemDao().insertPositionItem(positionEntityMapper.map(balance, accountId))
                    } else {
                        dbMem.positionItemDao().updatePositionItem(positionEntityMapper.map(balance, accountId))
                        idDeleteList.remove(balance.id!!)
                    }
                })
                dbMem.positionItemDao().deleteIds(idDeleteList)
                return true
            }

        })
    }
}