package uk.co.sentinelweb.bitwatcher.database.mapper

import uk.co.sentinelweb.bitwatcher.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import javax.inject.Inject

class TickerEntityToDomainMapper @Inject constructor(){
    fun map(input: TickerEntity): TickerDomain {
        return TickerDomain(input.dateStamp, input.amount, input.currencyCode, input.baseCode)
    }
}