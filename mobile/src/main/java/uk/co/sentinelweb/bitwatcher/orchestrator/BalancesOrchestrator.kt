package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountSaveInteractor
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.domain.AccountDomain
import uk.co.sentinelweb.bitwatcher.domain.AccountType
import uk.co.sentinelweb.bitwatcher.domain.BalanceDomain
import uk.co.sentinelweb.bitwatcher.net.BalanceApiInteractor
import uk.co.sentinelweb.bitwatcher.net.NetModule
import javax.inject.Inject
import javax.inject.Named

class BalancesOrchestrator @Inject constructor(
        private @Named(NetModule.BITSTAMP) var balancesInteractor: BalanceApiInteractor,
        private val db: BitwatcherDatabase,
        private val accountSaveInteractor: AccountSaveInteractor,
        private val accountDomainMapper: AccountEntityToDomainMapper

) {
    fun getBalances(): Single<Boolean> {
        return balancesInteractor.getAccountBalance()
                .zipWith(db.fullAccountDao()
                        .singleAccountsOfType(AccountType.BITSTAMP)
                        .map { fullAccountList -> accountDomainMapper.mapFull(fullAccountList.get(0)) },
                        BiFunction({ balances: List<BalanceDomain>, account: AccountDomain -> Pair(balances, account) }))
                .map { pair -> pair.second.copy(balances = pair.first)}
                .flatMap { accountUpdated -> accountSaveInteractor.save(accountUpdated) }

    }
}