package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Observable
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.net.NetModule.Companion.BINANCE
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
        private @Named(BINANCE) val bnTradesInteractor: TradeDataInteractor,
        private @Named(BINANCE) val bnTransactionsInteractor: TransactionsDataInteractor,
        private val db: BitwatcherDatabase,
        private val accountDomainMapper: AccountEntityToDomainMapper
) : GetTransactionsUseCase {

    override fun getTransactionsForAccount(account: AccountDomain, type: GetTransactionsUseCase.Type?)
            : Observable<AccountDomain> {
        return when (account.type) {
            AccountType.BITSTAMP -> {
                return transactionsForAccountObservable(account, bsTradesInteractor.getUserTrades(), bsTransactionsInteractor.getTransactions().toObservable())
            }
            AccountType.BINANCE -> {
                // TODO take pairs from the balances
                //val currencyPairs = listOf(CurrencyPair(CurrencyCode.IOTA, CurrencyCode.BTC))
                // bnTradesInteractor.getUserTradesForPairs(currencyPairs)
                // TODO wait for xchange 4.3.2 to fix a bug
                //bnTransactionsInteractor.getTransactionsForCurrencies(listOf(CurrencyCode.BTC))
                return transactionsForAccountObservable(account, Observable.empty(),  Observable.empty())
            }
            else -> Observable.empty()
        }
    }

    override fun getAllTransactionsByAccount(type: GetTransactionsUseCase.Type?)
            : Observable<AccountDomain> {
        return db.fullAccountDao()
                .singleAllAccounts()
                .toObservable()
                .map { entityList -> accountDomainMapper.mapFullList(entityList) }
                .flatMap { accountList -> Observable.fromIterable(accountList) }
                .flatMap { acc: AccountDomain -> getTransactionsForAccount(acc, type) }
    }

    private fun transactionsForAccountObservable(
            account: AccountDomain,
            tradesObservable: Observable<List<TransactionItemDomain.TradeDomain>>,
            transactionsObservable: Observable<List<TransactionItemDomain.TransactionDomain>>?)
            : Observable<AccountDomain> {
        return Observable.mergeDelayError(
                tradesObservable,
                transactionsObservable
        ).collectInto(mutableListOf(), { collect: MutableList<TransactionItemDomain>, add: List<TransactionItemDomain> -> collect.addAll(add) })
                .map { mutableList -> account.copy(tranasactions = mutableList.toList()) }
                .toObservable()
    }
}