package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Observable
import uk.co.sentinelweb.bitwatcher.net.NetModule.Companion.BITSTAMP
import uk.co.sentinelweb.bitwatcher.net.TradeDataInteractor
import uk.co.sentinelweb.bitwatcher.net.TransactionsDataInteractor
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.domain.TransactionItemDomain
import uk.co.sentinelweb.use_case.GetTransactionsUseCase
import javax.inject.Inject
import javax.inject.Named

class GetTransactionsOrchestrator @Inject constructor(
       private @Named(BITSTAMP) val bsTradesInteractor: TradeDataInteractor,
       private @Named(BITSTAMP) val bsTransactionsInteractor: TransactionsDataInteractor

) : GetTransactionsUseCase {

    override fun getTransactionsForAccount(account: AccountDomain, type: GetTransactionsUseCase.Type?): Observable<List<TransactionItemDomain>> {
        return Observable.empty()
    }

    override fun getTransactions(type: GetTransactionsUseCase.Type?): Observable<List<TransactionItemDomain>> {
        return Observable.merge(
                bsTradesInteractor.getUserTrades(),
                bsTransactionsInteractor.getTransactions().toObservable()
        )
    }
}