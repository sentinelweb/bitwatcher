package uk.co.sentinelweb.bitwatcher.database.test

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode.*
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject


class DbInitialiser @Inject constructor(val db: BitwatcherDatabase) {
    fun init(): Single<Boolean> {
        return Single.fromCallable({
            if (db.tickerDao().count() == 0) {
                db.tickerDao().insertTicker(TickerEntity(1, "basic", BTC, USD, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(2, "basic", BTC, GBP, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(3, "basic", BTC, EUR, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(4, "basic", ETH, USD, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(5, "basic", ETH, GBP, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(6, "basic", ETH, EUR, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(7, "basic", BCH, USD, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(8, "basic", BCH, GBP, BigDecimal.ZERO, Date()))
                db.tickerDao().insertTicker(TickerEntity(9, "basic", BCH, EUR, BigDecimal.ZERO, Date()))
            }
            true
        })

    }
}