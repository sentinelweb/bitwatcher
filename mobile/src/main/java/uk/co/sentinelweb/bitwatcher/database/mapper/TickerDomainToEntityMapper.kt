package uk.co.sentinelweb.bitwatcher.database.mapper

import uk.co.sentinelweb.bitwatcher.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import javax.inject.Inject

class TickerDomainToEntityMapper @Inject constructor() {
    fun map(input:TickerDomain ):TickerEntity  {
        return TickerEntity(0, "", input.currencyCode, input.baseCurrencyCode, input.last,input.from)
    }
}