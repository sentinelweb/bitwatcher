package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Maybe
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.CurrencyCode.*
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.use_case.MarketsDataUseCase
import javax.inject.Inject

class MarketDataInteractor @Inject constructor(): MarketsDataUseCase{
    override fun getMarkets(account: AccountDomain): Maybe<List<CurrencyPair>> {
        if (account.type == AccountType.GHOST) {
            return Maybe.just(listOf(
                    CurrencyPair(BTC, USD),
                    CurrencyPair(BTC, GBP),
                    CurrencyPair(BTC, EUR),
                    CurrencyPair(ETH, USD),
                    CurrencyPair(ETH, GBP),
                    CurrencyPair(ETH, EUR),
                    CurrencyPair(BCH, USD),
                    CurrencyPair(BCH, GBP),
                    CurrencyPair(BCH, EUR),
                    CurrencyPair(XRP, USD),
                    CurrencyPair(XRP, GBP),
                    CurrencyPair(XRP, EUR),
                    CurrencyPair(IOTA, USD),
                    CurrencyPair(IOTA, GBP),
                    CurrencyPair(IOTA, EUR)
            ))
        } else {
            return Maybe.empty<List<CurrencyPair>>()
        }
    }


}