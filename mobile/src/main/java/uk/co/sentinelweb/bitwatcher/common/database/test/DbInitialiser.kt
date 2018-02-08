package uk.co.sentinelweb.bitwatcher.common.database.test

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.domain.CurrencyCode.*
import uk.co.sentinelweb.domain.TickerDomain
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject


class DbInitialiser @Inject constructor(private val db: BitwatcherDatabase) {
    fun init(): Single<Boolean> {
        return Single.fromCallable({
            if (db.tickerDao().count() == 0) {
                db.tickerDao().insertTicker(TickerEntity(1, TickerDomain.NAME_CURRENT, BTC, USD, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(2, TickerDomain.NAME_CURRENT, BTC, GBP, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(3, TickerDomain.NAME_CURRENT, BTC, EUR, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(4, TickerDomain.NAME_CURRENT, ETH, USD, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(5, TickerDomain.NAME_CURRENT, ETH, GBP, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(6, TickerDomain.NAME_CURRENT, ETH, EUR, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(7, TickerDomain.NAME_CURRENT, BCH, USD, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(8, TickerDomain.NAME_CURRENT, BCH, GBP, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(9, TickerDomain.NAME_CURRENT, BCH, EUR, BigDecimal.ZERO, Date()))
            }
            true
        })

    }
}