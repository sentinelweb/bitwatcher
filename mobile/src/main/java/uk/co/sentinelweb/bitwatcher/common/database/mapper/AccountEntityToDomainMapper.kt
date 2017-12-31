package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.AccountEntity
import uk.co.sentinelweb.bitwatcher.common.database.entities.FullAccountView
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.ColourDomain
import javax.inject.Inject

class AccountEntityToDomainMapper @Inject constructor(
        private val positionMapper: PositionEntityToDomainMapper
) {
    fun map(input: AccountEntity): AccountDomain {
        return AccountDomain(input.id, input.name, input.type, listOf(), colour = input.colour?: ColourDomain.TRANSPARENT)
    }

    fun mapFull(input: FullAccountView): AccountDomain {
        return AccountDomain(input.id, input.name, input.type, positionMapper.mapList(input.balances), colour = input.colour)
    }

    fun mapFullList(input: List<FullAccountView>): List<AccountDomain> {
        val output = mutableListOf<AccountDomain>()
        input.forEach({ entity -> output.add(mapFull(entity)) })
        return output.toList()
    }
}