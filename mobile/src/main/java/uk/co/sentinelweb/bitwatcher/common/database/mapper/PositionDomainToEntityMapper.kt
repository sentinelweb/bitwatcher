package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.PositionItemEntity
import uk.co.sentinelweb.bitwatcher.domain.BalanceDomain
import javax.inject.Inject

class PositionDomainToEntityMapper @Inject constructor(){
    fun map(input: BalanceDomain, accountId:Long):  PositionItemEntity{
        return PositionItemEntity(input.id, accountId, input.currency, input.balance, input.available, input.reserved)
    }

    fun mapList(input: List<BalanceDomain>, accountId:Long): List< PositionItemEntity> {
        val result = mutableListOf<PositionItemEntity>()
        input.forEach({p->result.add(map(p, accountId))})
        return result.toList()
    }
}