package uk.co.sentinelweb.bitwatcher.net.xchange.binance

import io.reactivex.Single
import org.knowm.xchange.binance.dto.account.BinanceBalance
import org.knowm.xchange.binance.service.BinanceAccountService
import uk.co.sentinelweb.bitwatcher.net.BalanceDataInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import java.util.concurrent.Callable

class BinanceBalanceApiInteractor(private val service: ExchangeService, private val mapper: BalanceMapper = BalanceMapper()): BalanceDataInteractor {

    override fun getAccountBalance(): Single<List<BalanceDomain>> {
        return Single.fromCallable(object : Callable<List<BalanceDomain>> {
            override fun call(): List<BalanceDomain> {
                val balances = (service.accountService as BinanceAccountService).account(10000, System.currentTimeMillis()).balances
                return mapper.map(balances)
            }
        })
    }

    class BalanceMapper {
        fun map(balances: Collection<BinanceBalance>): List<BalanceDomain> {
            val mutableListOf = mutableListOf<BalanceDomain>()
            for (xbalance in balances) {
                if (xbalance.free.abs().compareTo(BigDecimal(0.000000001))>0 || xbalance.locked.abs().compareTo(BigDecimal(0.000000001))>0) {
                    mutableListOf.add(BalanceDomain(
                            null,
                            CurrencyCode.lookup(xbalance.asset),
                            xbalance.free + xbalance.locked,
                            xbalance.free,
                            xbalance.locked))
                }
            }
            return mutableListOf.toList()
        }
    }
}