package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.domain.TickerDomain
import javax.inject.Inject

class TickerDomainToEntityMapper @Inject constructor() {

    fun map(input:TickerDomain ):TickerEntity  {
        return TickerEntity(0, input.name, input.currencyCode, input.baseCurrencyCode, input.last, input.from)
    }
}