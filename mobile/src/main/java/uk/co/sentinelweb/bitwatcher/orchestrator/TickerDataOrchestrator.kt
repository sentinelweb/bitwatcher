package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Flowable
import io.reactivex.Observable
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor
import uk.co.sentinelweb.domain.CurrencyCode.*
import uk.co.sentinelweb.domain.TickerDomain
import uk.co.sentinelweb.use_case.UpdateTickersUseCase
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class TickerDataOrchestrator @Inject constructor(
        private val tickersInteractor: TickerMergeInteractor,
        private val db: BitwatcherDatabase,
        private val tickerEntityMapper: TickerDomainToEntityMapper,
        private val tickerDomainMapper: TickerEntityToDomainMapper
) : UpdateTickersUseCase {

    override fun downloadTickerToRepository(): Observable<TickerDomain> {
        return tickersInteractor.getMergedTickers()
                .doOnNext { domain ->
                    val loadTicker = db.tickerDao().loadTickerNamed(domain.currencyCode, domain.baseCurrencyCode, TickerDomain.NAME_CURRENT)
                    val loadTickerPrev = db.tickerDao().loadTickerNamed(domain.currencyCode, domain.baseCurrencyCode, TickerDomain.NAME_PREVIOUS)
                    if (loadTicker != null) {
                        db.tickerDao().updateTicker(
                                domain.currencyCode,
                                domain.baseCurrencyCode,
                                TickerDomain.NAME_CURRENT,
                                domain.last,
                                domain.from)
                    } else {
                        db.tickerDao().insertTicker(tickerEntityMapper.map(domain))
                    }
                    if (loadTickerPrev != null) {
                        db.tickerDao().updateTicker(
                                domain.currencyCode,
                                domain.baseCurrencyCode,
                                TickerDomain.NAME_PREVIOUS,
                                loadTicker?.amount?: BigDecimal.ZERO,
                                loadTicker?.dateStamp?:Date())
                    } else {
                        val newPreviousEntity =
                                tickerEntityMapper
                                        .map(domain)
                                        .copy(name = TickerDomain.NAME_PREVIOUS)
                        db.tickerDao().insertTicker(newPreviousEntity)
                    }
                }
    }

    override fun observeTickersFromRepository(): Observable<TickerDomain> {
        return Flowable.merge(
                Flowable.merge(
                        db.tickerDao().flowTicker(BTC, GBP),
                        db.tickerDao().flowTicker(BTC, EUR),
                        db.tickerDao().flowTicker(BTC, USD)
                ),
                Flowable.merge(
                        db.tickerDao().flowTicker(ETH, GBP),
                        db.tickerDao().flowTicker(ETH, EUR),
                        db.tickerDao().flowTicker(ETH, USD)
                ),
                Flowable.merge(
                        db.tickerDao().flowTicker(BCH, GBP),
                        db.tickerDao().flowTicker(BCH, EUR),
                        db.tickerDao().flowTicker(BCH, USD)
                ),
                Flowable.merge(
                        Flowable.merge(
                                db.tickerDao().flowTicker(XRP, GBP),
                                db.tickerDao().flowTicker(XRP, EUR),
                                db.tickerDao().flowTicker(XRP, USD)
                        ),

                        Flowable.merge(
                                db.tickerDao().flowTicker(IOTA, GBP),
                                db.tickerDao().flowTicker(IOTA, EUR),
                                db.tickerDao().flowTicker(IOTA, USD)
                        )
                )
        ).map({ tickerEntity -> tickerDomainMapper.map(tickerEntity) })
                .toObservable()
    }
}