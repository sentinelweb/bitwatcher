package uk.co.sentinelweb.use_case

import io.reactivex.Observable
import uk.co.sentinelweb.domain.TickerDomain


interface UpdateTickersUseCase {
    fun downloadTickerToRepository(): Observable<TickerDomain>

    fun observeTickersFromRepository(): Observable<TickerDomain>
}
