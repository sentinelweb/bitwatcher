package uk.co.sentinelweb.bitwatcher.database.mapper

import uk.co.sentinelweb.bitwatcher.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import javax.inject.Inject

class TickerEntityToDomainListMapper @Inject constructor(
        val tickerEntityToDomainMapper: TickerEntityToDomainMapper ) {
    fun map(entities : List<TickerEntity>):List<TickerDomain> {
        val result = mutableListOf<TickerDomain>()
        for (entity in entities) {
            result.add(tickerEntityToDomainMapper.map(entity))
        }
        return result
    }
}