package uk.co.sentinelweb.bitwatcher.common.database.test

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import javax.inject.Inject

class DbMemoryInitialiser @Inject constructor(private val db: BitwatcherDatabase) {
    fun init(): Single<Boolean> {
        return Single.fromCallable({
//            db.accountDao().insertAccount(AccountEntity(null, "Bitstamp", AccountType.GHOST))
//            db.positionItemDao().insertPositionItem(PositionItemEntity(null, CurrencyCode.BTC, BigDecimal("3.6789"), BigDecimal("3.6789"), BigDecimal("3.6789"), 1))
            true
        })

    }
}