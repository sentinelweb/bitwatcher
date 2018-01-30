package uk.co.sentinelweb.bitwatcher.common.database.interactor

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.entities.TradeEntity
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TradeDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TradeEntityToDomainMapper
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeStatus.COMPLETE
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeStatus.PENDING
import java.util.*
import java.util.concurrent.Callable
import javax.inject.Inject

class TradeDatabaseInteractor @Inject internal constructor(
        private val db: BitwatcherDatabase,
        private val domainMapper: TradeEntityToDomainMapper,
        private val entityMapper: TradeDomainToEntityMapper
) {
    fun singleInsertOrUpdate(account: AccountDomain, tradeDomain: TradeDomain): Single<TradeDomain> {
        val trade = entityMapper.map(tradeDomain, account)
        return Single.fromCallable(object : Callable<TradeEntity> {
            override fun call(): TradeEntity {
                if (trade.id == null) {
                    val checkTradeInDb = db.tradeDao().checkTradeInDb(trade.accountId, trade.tid)
                    if (checkTradeInDb != null) {
                        return checkTradeInDb
                    } else {
                        val id = db.tradeDao().insertTrade(trade)
                        return trade.copy(id = id)
                    }
                } else {
                    db.tradeDao().updateTrade(trade)
                    return trade
                }
            }

        }).map { entity -> domainMapper.map(entity) }
    }

    fun singleOpenTradesForAccount(acct: AccountDomain): Single<List<TradeDomain>> {
        return singleTradesForAccountWithOutStatuses(acct, COMPLETE)
    }

    fun singlePendingTradesForAccount(acct: AccountDomain): Single<List<TradeDomain>> {
        return singleTradesForAccountWithStatuses(acct, PENDING)
    }

    fun singleTradesForAccountWithOutStatuses(acct: AccountDomain, vararg exclude: TradeDomain.TradeStatus): Single<List<TradeDomain>> {
        return db.tradeDao()
                .singleTradesForAccountWithOutStatuses(acct.id!!, statusList(*exclude))
                .map { entityList: List<TradeEntity> -> domainMapper.mapList(entityList) }
    }

    fun singleTradesForAccountWithStatuses(acct: AccountDomain, vararg include: TradeDomain.TradeStatus): Single<List<TradeDomain>> {
        return db.tradeDao()
                .singleTradesForAccountWithStatuses(acct.id!!, statusList(*include))
                .map { entityList: List<TradeEntity> -> domainMapper.mapList(entityList) }
    }

    /**
     * Deletes a set of trades from the specified account
     */
    fun singleDeleteTrades(account: AccountDomain, trades: Set<TradeDomain>): Single<Boolean> {
        return Single.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                var result = true
                trades.forEach { trade ->
                    val tradeEntity = entityMapper.map(trade, account)
                    result = result && db.tradeDao().deleteTrade(tradeEntity.accountId, tradeEntity.tid) == 1

                }
                return result
            }
        })

    }

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private fun statusList(vararg list: TradeDomain.TradeStatus): java.util.List<TradeDomain.TradeStatus> {
        @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNCHECKED_CAST")
        val arrayList: java.util.List<TradeDomain.TradeStatus> = ArrayList<TradeDomain.TradeStatus>() as java.util.List<TradeDomain.TradeStatus>
        arrayList.addAll(list)
        return arrayList
    }

}
