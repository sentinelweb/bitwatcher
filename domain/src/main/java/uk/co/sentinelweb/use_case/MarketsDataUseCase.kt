package uk.co.sentinelweb.use_case

import io.reactivex.Maybe
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyPair

interface MarketsDataUseCase {
    fun getMarkets(account: AccountDomain): Maybe<List<CurrencyPair>>
}