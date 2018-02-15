package uk.co.sentinelweb.bitwatcher.orchestrator

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import uk.co.sentinelweb.bitwatcher.common.repository.BalanceRepository
import uk.co.sentinelweb.bitwatcher.net.BalanceDataInteractor
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.domain.TransactionItemDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType.BID
import uk.co.sentinelweb.use_case.AccountsRepositoryUseCase
import uk.co.sentinelweb.use_case.BalanceUpdateUseCase
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Named

private typealias AccountData = Pair<List<BalanceDomain>, AccountDomain>

class BalancesOrchestrator @Inject constructor(
        private @Named(NetModule.BITSTAMP) var balancesInteractorBitstamp: BalanceDataInteractor,
        private @Named(NetModule.BINANCE) var balancesInteractorBinance: BalanceDataInteractor,
        private val accountInteractor: AccountsRepositoryUseCase,
        private val balanceRepository: BalanceRepository

) : BalanceUpdateUseCase {


    override fun getBalances(): Observable<Boolean> {

        val saveBitstampBalances = Maybe.zip(
                accountInteractor.maybeLoadAccountOfType(AccountType.BITSTAMP),
                balancesInteractorBitstamp.getAccountBalance().toMaybe(),
                BiFunction({ account: AccountDomain, balances: List<BalanceDomain> -> Pair(balances, account) })
        )

        val saveBinanceBalances = Maybe.zip(
                accountInteractor.maybeLoadAccountOfType(AccountType.BINANCE),
                balancesInteractorBinance.getAccountBalance().toMaybe(),
                BiFunction({ account: AccountDomain, balances: List<BalanceDomain> -> Pair(balances, account) })
        )

        return Maybe
                .merge(saveBitstampBalances, saveBinanceBalances)
                .compose(SaveAccountTransformer())
                .toObservable()
    }

    private inner class SaveAccountTransformer : FlowableTransformer<AccountData, Boolean> {
        override fun apply(upstream: Flowable<AccountData>): Flowable<Boolean> {
            return upstream.map { pair -> pair.second.copy(balances = pair.first) }
                    .flatMap { accountUpdated -> accountInteractor.saveAccount(accountUpdated).toFlowable() }
        }
    }

    override fun updateBalanceFromTrade(acct: AccountDomain, trade: TransactionItemDomain.TradeDomain): Maybe<Boolean> {
        return accountInteractor
                .singleLoadAccount(acct.id!!)
                .map { acctLoaded ->
                    val multiplier = if (trade.type == BID) -BigDecimal.ONE else BigDecimal.ONE
                    val balanceFrom = acctLoaded.balances.filter { bal -> bal.currency == trade.currencyCodeFrom }.first()
                    val newBalanceFrom = balanceFrom.copy(balance = balanceFrom.balance - trade.amount * multiplier)
                    balanceRepository.saveBalance(acctLoaded.id!!, newBalanceFrom)

                    val balanceTo = acctLoaded.balances.filter { bal -> bal.currency == trade.currencyCodeTo }.first()
                    val newBalanceTo = balanceTo.copy(balance = balanceTo.balance + trade.amount * trade.price * multiplier)
                    balanceRepository.saveBalance(acctLoaded.id!!, newBalanceTo)

//                    val newBalances = acctLoaded.balances.toMutableList()
//                    newBalances.map({ balance ->
//                        if (balance.id == newBalanceFrom.id) newBalanceFrom
//                        else if (balance.id == newBalanceTo.id) newBalanceTo
//                        else balance
//                    })
//                    acctLoaded = acctLoaded.copy(balances = newBalances.toList())
                    true // to change return type
                }
                .toMaybe()
    }
}