package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import uk.co.sentinelweb.bitwatcher.common.database.mapper.AccountEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp.BalanceApiInteractor
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.use_case.BalanceUpdateUseCase
import javax.inject.Inject
import javax.inject.Named

class BalancesOrchestrator @Inject constructor(
        private @Named(NetModule.BITSTAMP) var balancesInteractor: BalanceApiInteractor,
        private val db: BitwatcherDatabase,
        private val accountInteractor: AccountInteractor,
        private val accountDomainMapper: AccountEntityToDomainMapper

) : BalanceUpdateUseCase{
    override fun getBalances(): Single<Boolean> {
        return balancesInteractor.getAccountBalance()
                .zipWith(db.fullAccountDao()
                        .singleAccountsOfType(AccountType.BITSTAMP)
                        .map { fullAccountList -> accountDomainMapper.mapFull(fullAccountList.get(0)) },
                        BiFunction({ balances: List<BalanceDomain>, account: AccountDomain -> Pair(balances, account) }))
                .map { pair -> pair.second.copy(balances = pair.first)}
                .flatMap { accountUpdated -> accountInteractor.saveAccount(accountUpdated) }

    }
}