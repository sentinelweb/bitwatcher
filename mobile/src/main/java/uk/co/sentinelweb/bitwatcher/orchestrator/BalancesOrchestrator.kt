package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.net.BalanceDataInteractor
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.use_case.BalanceUpdateUseCase
import javax.inject.Inject
import javax.inject.Named

class BalancesOrchestrator @Inject constructor(
        private @Named(NetModule.BITSTAMP) var balancesInteractorBitstamp: BalanceDataInteractor,
        private @Named(NetModule.BINANCE) var balancesInteractorBinance: BalanceDataInteractor,
        private val db: BitwatcherDatabase,
        private val accountInteractor: AccountInteractor,
        private val accountDomainMapper: AccountEntityToDomainMapper

) : BalanceUpdateUseCase {
    override fun getBalances(): Observable<Boolean> {

        // TODO singleAccountsOfType should be Maybe and how to zip that with observable

        val saveBitstampBalances = Maybe.zip(
                balancesInteractorBitstamp.getAccountBalance().toMaybe(),
                accountInteractor.maybeLoadAccountOfType(AccountType.BITSTAMP),
                BiFunction({ balances: List<BalanceDomain>, account: AccountDomain -> Pair(balances, account) })
        )

        val saveBinanceBalances = Maybe.zip(
                balancesInteractorBinance.getAccountBalance().toMaybe(),
                accountInteractor.maybeLoadAccountOfType(AccountType.BINANCE),
                BiFunction({ balances: List<BalanceDomain>, account: AccountDomain -> Pair(balances, account) })
        )

        return Maybe
                .merge(saveBitstampBalances, saveBinanceBalances)
                .compose(SaveAccountTransformer())
                .toObservable()
    }

    inner class SaveAccountTransformer : FlowableTransformer<Pair<List<BalanceDomain>, AccountDomain>, Boolean> {
        override fun apply(upstream: Flowable<Pair<List<BalanceDomain>, AccountDomain>>): Flowable<Boolean> {
            return upstream.map { pair -> pair.second.copy(balances = pair.first) }
                    .flatMap { accountUpdated -> accountInteractor.saveAccount(accountUpdated).toFlowable() }
        }

    }
}