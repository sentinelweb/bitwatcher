package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Flowable
import io.reactivex.Observable
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor
import uk.co.sentinelweb.domain.CurrencyCode.*
import uk.co.sentinelweb.domain.TickerDomain
import javax.inject.Inject

class TickerDataOrchestrator @Inject constructor(
        private val tickersInteractor: TickerMergeInteractor,
        private val db: BitwatcherDatabase,
        private val entityMapper: TickerDomainToEntityMapper) {

    fun downloadTickerToDatabase(): Observable<TickerEntity> {
        return tickersInteractor.getMergedTickers()
                .map { domain -> entityMapper.map(domain) }
                .doOnNext { entity ->
                    val loadTicker = db.tickerDao().loadTicker(entity.currencyCode, entity.baseCode)
                    if (loadTicker != null) {
                        db.tickerDao().updateTicker(entity.currencyCode, entity.baseCode, entity.amount, entity.dateStamp)
                        db.tickerDao().updateName(entity.currencyCode, entity.baseCode, TickerDomain.BASIC)
                    } else {
                        db.tickerDao().insertTicker(entity)
                    }
                }
    }

    fun flowTickers(): Flowable<TickerEntity> {
        return Flowable.merge(
                Flowable.merge(
                        db.tickerDao().flowTicker(BTC.toString(), GBP.toString()),
                        db.tickerDao().flowTicker(BTC.toString(), EUR.toString()),
                        db.tickerDao().flowTicker(BTC.toString(), USD.toString())
                ),
                Flowable.merge(
                        db.tickerDao().flowTicker(ETH.toString(), GBP.toString()),
                        db.tickerDao().flowTicker(ETH.toString(), EUR.toString()),
                        db.tickerDao().flowTicker(ETH.toString(), USD.toString())
                ),
                Flowable.merge(
                        db.tickerDao().flowTicker(BCH.toString(), GBP.toString()),
                        db.tickerDao().flowTicker(BCH.toString(), EUR.toString()),
                        db.tickerDao().flowTicker(BCH.toString(), USD.toString())
                ),
                Flowable.merge(
                        Flowable.merge(
                                db.tickerDao().flowTicker(XRP.toString(), GBP.toString()),
                                db.tickerDao().flowTicker(XRP.toString(), EUR.toString()),
                                db.tickerDao().flowTicker(XRP.toString(), USD.toString())
                        ),

                        Flowable.merge(
                                db.tickerDao().flowTicker(IOTA.toString(), GBP.toString()),
                                db.tickerDao().flowTicker(IOTA.toString(), EUR.toString()),
                                db.tickerDao().flowTicker(IOTA.toString(), USD.toString())
                        )

                )
        )
    }
}