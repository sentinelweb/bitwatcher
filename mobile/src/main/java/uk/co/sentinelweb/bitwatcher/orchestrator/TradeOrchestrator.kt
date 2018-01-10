package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Single
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain
import uk.co.sentinelweb.use_case.TradeUseCase
import javax.inject.Inject

class TradeOrchestrator @Inject constructor(): TradeUseCase {

    override fun placeTrade(account: AccountDomain, trade: TransactionItemDomain.TradeDomain): Single<TransactionItemDomain.TradeDomain> {
        return Single.just(trade)
    }

}