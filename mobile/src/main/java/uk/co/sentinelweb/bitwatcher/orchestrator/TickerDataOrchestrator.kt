package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Observable
import uk.co.sentinelweb.bitwatcher.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.database.mapper.TickerDomainToEntityMapper
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
}