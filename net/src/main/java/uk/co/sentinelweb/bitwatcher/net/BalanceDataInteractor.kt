package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Single
import uk.co.sentinelweb.domain.BalanceDomain

interface BalanceDataInteractor {
    fun getAccountBalance(): Single<List<BalanceDomain>>
}