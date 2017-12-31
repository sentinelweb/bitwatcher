package uk.co.sentinelweb.use_case

import io.reactivex.Observable

interface BalanceUpdateUseCase {
    fun getBalances(): Observable<Boolean>
}