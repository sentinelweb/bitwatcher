package uk.co.sentinelweb.bitwatcher.common.database.test

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherMemoryDatabase
import uk.co.sentinelweb.bitwatcher.common.database.entities.AccountEntity
import uk.co.sentinelweb.bitwatcher.common.database.entities.PositionItemEntity
import uk.co.sentinelweb.bitwatcher.domain.AccountType
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import java.math.BigDecimal
import javax.inject.Inject

class DbMemoryInitialiser @Inject constructor(val db: BitwatcherMemoryDatabase) {
    fun init(): Single<Boolean> {
        return Single.fromCallable({
            db.accountDao().insertAccount(AccountEntity(null, "Bitstamp", AccountType.GHOST))
            db.positionItemDao().insertPositionItem(PositionItemEntity(null, CurrencyCode.BTC, BigDecimal("3.6789"), 1))
            true
        })

    }
}