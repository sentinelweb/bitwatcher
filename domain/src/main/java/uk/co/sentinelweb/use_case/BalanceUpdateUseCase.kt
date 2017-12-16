package uk.co.sentinelweb.use_case

import io.reactivex.Single

interface BalanceUpdateUseCase {
    fun getBalances(): Single<Boolean>
}