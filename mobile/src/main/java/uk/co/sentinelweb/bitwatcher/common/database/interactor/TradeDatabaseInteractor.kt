package uk.co.sentinelweb.bitwatcher.common.database.interactor

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.entities.TradeEntity
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TradeDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TradeEntityToDomainMapper
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain
import java.util.concurrent.Callable
import javax.inject.Inject

class TradeDatabaseInteractor @Inject internal constructor(
        private val db: BitwatcherDatabase,
        private val domainMapper: TradeEntityToDomainMapper,
        private val entityMapper: TradeDomainToEntityMapper
) {
    fun singleInsertOrUpdate(account: AccountDomain, tradeDomain: TransactionItemDomain.TradeDomain): Single<TransactionItemDomain.TradeDomain> {
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

        }).map {entity -> domainMapper.map(entity)  }
    }
}
