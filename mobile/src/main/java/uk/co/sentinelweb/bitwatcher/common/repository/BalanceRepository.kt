package uk.co.sentinelweb.bitwatcher.common.repository

import uk.co.sentinelweb.domain.BalanceDomain

interface BalanceRepository {
    fun saveBalance(accountId:Long, balance:BalanceDomain)
}