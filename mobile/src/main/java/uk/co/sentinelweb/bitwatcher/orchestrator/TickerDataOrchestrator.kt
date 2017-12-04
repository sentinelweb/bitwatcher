package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Flowable
import io.reactivex.Observable
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode.*
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor
import javax.inject.Inject

class TickerDataOrchestrator @Inject constructor(
        private val tickersInteractor: TickerMergeInteractor,
        private val db: BitwatcherDatabase,
        private val entityMapper: TickerDomainToEntityMapper) {

    fun downloadTickerToDatabase(): Observable<TickerEntity> {
        return tickersInteractor.getMergedTickers()
                .filter({ t -> t != null })
                .map { domain -> entityMapper.map(domain) }
                .doOnNext { entity -> db.tickerDao().updateTicker(entity.currencyCode, entity.baseCode, entity.amount, entity.dateStamp) }
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
                )
        )
    }
}