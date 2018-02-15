package uk.co.sentinelweb.bitwatcher.common.database.repository

import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.PositionDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.common.repository.BalanceRepository
import uk.co.sentinelweb.domain.BalanceDomain
import javax.inject.Inject

class PositionItemRoomRepository @Inject constructor(
        private val db: BitwatcherDatabase,
        private val positionEntityMapper: PositionDomainToEntityMapper
): BalanceRepository {

    override fun saveBalance(accountId:Long, balance: BalanceDomain) {
        if (balance.id == null) {
            db.positionItemDao().insertPositionItem(positionEntityMapper.map(balance, accountId))
        } else {
            db.positionItemDao().updatePositionItem(positionEntityMapper.map(balance, accountId))
        }
    }
}