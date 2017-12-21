package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.net.NetModule.Companion.BITSTAMP
import uk.co.sentinelweb.bitwatcher.net.TradeDataInteractor
import uk.co.sentinelweb.bitwatcher.net.TransactionsDataInteractor
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.TransactionItemDomain
import uk.co.sentinelweb.use_case.GetTransactionsUseCase
import javax.inject.Inject
import javax.inject.Named

class GetTransactionsOrchestrator @Inject constructor(
        private @Named(BITSTAMP) val bsTradesInteractor: TradeDataInteractor,
        private @Named(BITSTAMP) val bsTransactionsInteractor: TransactionsDataInteractor,
        private val db: BitwatcherDatabase,
        private val accountDomainMapper: AccountEntityToDomainMapper

) : GetTransactionsUseCase {

    override fun getTransactionsForAccount(account: AccountDomain, type: GetTransactionsUseCase.Type?): Observable<List<TransactionItemDomain>> {
        return Observable.empty()
    }

    override fun getAllTransactionsByAccount(type: GetTransactionsUseCase.Type?): Observable<AccountDomain> {
        val mergedBsTransactions = Observable.merge(
                bsTradesInteractor.getUserTrades(),
                bsTransactionsInteractor.getTransactions().toObservable()
        ).collectInto(mutableListOf<TransactionItemDomain>(), {lista:MutableList<TransactionItemDomain>, listt:List<TransactionItemDomain> -> lista.addAll(listt)})
                .toObservable()

        return Observable.zip(db.fullAccountDao()
                //.singleAllAccounts()
                .singleAccountsOfType(AccountType.BITSTAMP)
                .toObservable()
                .flatMap { accountList -> Observable.fromIterable(accountList) }
                .take(1)
                .map { entity -> accountDomainMapper.mapFull(entity) },
                mergedBsTransactions,
                BiFunction { acc: AccountDomain, listt: List<TransactionItemDomain> -> acc.copy(tranasactions = listt) })

    }
}