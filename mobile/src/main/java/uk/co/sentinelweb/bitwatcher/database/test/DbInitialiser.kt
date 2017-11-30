package uk.co.sentinelweb.bitwatcher.database.test

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.database.entities.Account
import uk.co.sentinelweb.bitwatcher.database.entities.PositionItem
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import java.math.BigDecimal
import javax.inject.Inject


class DbInitialiser @Inject constructor(val db: BitwatcherDatabase) {
    fun init(): Single<Boolean> {
        return Single.fromCallable({
            db.accountDao().insertAccount(Account(null, "Bitstamp"))
            db.positionItemDao().insertPositionItem(PositionItem(null, CurrencyCode.BTC, BigDecimal("3.6789"), 1))
            true
        })

    }
}