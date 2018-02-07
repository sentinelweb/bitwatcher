package uk.co.sentinelweb.use_case

import io.reactivex.Single
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain
import java.math.BigDecimal

interface TickerUseCase {
    fun getRate(currency: CurrencyCode, base: CurrencyCode, name:String = TickerDomain.NAME_CURRENT): Single<BigDecimal>
    fun getRateRange(currency: CurrencyCode, base: CurrencyCode): Single<Pair<BigDecimal, BigDecimal>>
}