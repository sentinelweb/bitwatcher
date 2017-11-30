package uk.co.sentinelweb.bitwatcher.database

import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.database.entities.Account
import uk.co.sentinelweb.bitwatcher.database.entities.PositionItem
import javax.inject.Inject


class DbInitialiser @Inject constructor(val db: BitwatcherDatabase) {
    fun init(): Single<Boolean> {
        return Single.fromCallable({
            db.accountDao().insertAccount(Account("1", "Bitstamp"))
            db.positionItemDao().insertPositionItem(PositionItem("1", "BTC", "3.6789", "1"))
            true
        })

    }
}