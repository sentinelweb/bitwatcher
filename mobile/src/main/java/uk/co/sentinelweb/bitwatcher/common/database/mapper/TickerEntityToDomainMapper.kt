package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.domain.TickerDomain
import javax.inject.Inject

class TickerEntityToDomainMapper @Inject constructor(){
    fun map(input: TickerEntity): TickerDomain {
        return TickerDomain(input.name, input.dateStamp, input.amount, input.currencyCode, input.baseCode)
    }
}