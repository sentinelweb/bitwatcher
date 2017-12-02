package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.AccountEntity
import uk.co.sentinelweb.bitwatcher.common.database.entities.FullAccountView
import uk.co.sentinelweb.bitwatcher.domain.AccoutDomain
import javax.inject.Inject

class AccountEntityToDomainMapper @Inject constructor(
    val positionMapper:PositionEntityToDomainMapper
){
    fun map(input: AccountEntity): AccoutDomain {
        return AccoutDomain(input.id,input.name,input.type, listOf())
    }

    fun mapFull(input: FullAccountView): AccoutDomain {
        return AccoutDomain(input.id,input.name,input.type, positionMapper.mapList(input.balances))
    }
}