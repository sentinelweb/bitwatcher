package uk.co.sentinelweb.bitwatcher.common.database.interactor

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.converter.BigDecimalConverter
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.common.extensions.div
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.CurrencyCode.*
import uk.co.sentinelweb.domain.TickerDomain
import uk.co.sentinelweb.use_case.TickerUseCase
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class TickerRateInteractor @Inject constructor(
        private val db: BitwatcherDatabase):TickerUseCase {
    override fun getRateRange(currency: CurrencyCode, base: CurrencyCode): Single<Pair<BigDecimal, BigDecimal>> {
        return Single.zip(
                getRate(currency, base, TickerDomain.NAME_CURRENT),
                getRate(currency, base, TickerDomain.NAME_PREVIOUS),
                BiFunction{cur:BigDecimal, prev:BigDecimal -> Pair(cur, prev)}
        )
    }

    override fun getRate(currency: CurrencyCode, base: CurrencyCode, name:String): Single<BigDecimal> {
        if (currency.type == Type.CRYPTO && base.type == Type.FIAT) {
            return getTickerSingle(currency, base)
                    .map { t: TickerEntity -> t.amount }
        } else if (currency.type == Type.FIAT && base.type == Type.CRYPTO) {
            return getTickerSingle(base, currency)
                    .map { t: TickerEntity -> BigDecimal.ONE.div(t.amount) }
        } else if (currency.type == Type.CRYPTO && base.type == Type.CRYPTO) {
            return Single.zip(
                    getTickerSingle(currency, USD),
                    getTickerSingle(base, USD),
                    BiFunction { tCurrency: TickerEntity, tBase: TickerEntity -> tCurrency.amount.div(tBase.amount) }
            )
        } else if (currency.type == Type.FIAT && base.type == Type.FIAT) {
            return Single.zip(
                    getTickerSingle(BTC, currency),
                    getTickerSingle(BTC, base),
                    BiFunction { tCurrency: TickerEntity, tBase: TickerEntity -> tBase.amount.div(tCurrency.amount) }
            )
        } else return Single.error(IllegalArgumentException("Unknown Conversion ${currency} ${base}"))
    }

    private fun getTickerSingle(currency: CurrencyCode, base: CurrencyCode, name:String = TickerDomain.NAME_CURRENT):Single<TickerEntity> {
        return db.tickerDao().singleTickerNamed(currency, base, name )
    }

}