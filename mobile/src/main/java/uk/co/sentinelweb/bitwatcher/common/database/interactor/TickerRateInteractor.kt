package uk.co.sentinelweb.bitwatcher.common.database.interactor

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.converter.BigDecimalConverter
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode.*
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class TickerRateInteractor @Inject constructor(
        private val db: BitwatcherDatabase) {

    fun getRate(currency: CurrencyCode, base: CurrencyCode): Single<BigDecimal> {
        if (  isCrypto(currency) &&isFiat(base)) {
            return db.tickerDao().singleTicker(currency.toString(),base.toString())
                    .map { t: TickerEntity -> t.amount }
        } else if (isFiat(currency) &&isCrypto(base)) {
            return db.tickerDao().singleTicker(base.toString(),currency.toString())
                    .map { t: TickerEntity -> BigDecimal.ONE.setScale(BigDecimalConverter.DEFUALT_SCALE).divide(t.amount, BigDecimalConverter.DEFUALT_SCALE, RoundingMode.HALF_EVEN) }
        } else if (isCrypto(currency) && isCrypto(base)) {
            return Single.zip(
                    db.tickerDao().singleTicker(currency.toString(), USD.toString()),
                    db.tickerDao().singleTicker(base.toString(), USD.toString()),
                    BiFunction { tCurrency:TickerEntity, tBase:TickerEntity -> tCurrency.amount.divide(tBase.amount, BigDecimalConverter.DEFUALT_SCALE, RoundingMode.HALF_EVEN)}
            )
        } else if (isFiat(currency) && isFiat(base)) {
            return Single.zip(
                    db.tickerDao().singleTicker(BTC.toString(), currency.toString()),
                    db.tickerDao().singleTicker(BTC.toString(), base.toString()),
                    BiFunction { tCurrency:TickerEntity, tBase:TickerEntity -> tBase.amount.divide(tCurrency.amount, BigDecimalConverter.DEFUALT_SCALE, RoundingMode.HALF_EVEN)}
            )
        } else return Single.error(IllegalArgumentException("Unknown Conversion ${currency} ${base}"))
    }

    private fun isFiat(from: CurrencyCode) = listOf(GBP, EUR, USD).contains(from)
    private fun isCrypto(from: CurrencyCode) = listOf(BTC, BCH, ETH).contains(from)
}