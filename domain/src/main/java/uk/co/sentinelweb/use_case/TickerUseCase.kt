package uk.co.sentinelweb.use_case

import io.reactivex.Single
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal

interface TickerUseCase {
    fun getRate(currency: CurrencyCode, base: CurrencyCode): Single<BigDecimal>
}