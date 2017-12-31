package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.AccountEntity
import uk.co.sentinelweb.bitwatcher.common.database.entities.FullAccountView
import uk.co.sentinelweb.domain.AccountDomain
import javax.inject.Inject

class AccountDomainToEntityMapper @Inject constructor(
        private val positionMapper: PositionEntityToDomainMapper
){
    fun map(input: AccountDomain): AccountEntity {
        return AccountEntity(input.id, input.name, input.type, input.colour)
    }

    fun mapFull(input: FullAccountView): AccountDomain {
        return AccountDomain(input.id, input.name, input.type, positionMapper.mapList(input.balances))
    }
}