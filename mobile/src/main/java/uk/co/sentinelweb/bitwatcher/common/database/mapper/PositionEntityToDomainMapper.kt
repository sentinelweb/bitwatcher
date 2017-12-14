package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.PositionItemEntity
import uk.co.sentinelweb.domain.BalanceDomain
import javax.inject.Inject

class PositionEntityToDomainMapper @Inject constructor(){
    fun map(input: PositionItemEntity): BalanceDomain {
        return BalanceDomain(input.id, input.currencyCode, input.amount, input.available,input.reserved)
    }

    fun mapList(input: List<PositionItemEntity>): List<BalanceDomain> {
        val result = mutableListOf<BalanceDomain>()
        input.forEach({p->result.add(map(p))})
        return result.toList()
    }
}