package uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp

import io.reactivex.Single
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance
import org.knowm.xchange.bitstamp.service.BitstampAccountServiceRaw
import uk.co.sentinelweb.bitwatcher.net.BalanceDataInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.domain.CurrencyCode
import java.util.concurrent.Callable

class BitstampBalanceApiInteractor(private val service: ExchangeService, private val mapper: BalanceMapper = BalanceMapper()):BalanceDataInteractor {

    override fun getAccountBalance(): Single<List<BalanceDomain>> {
        return Single.fromCallable(object : Callable<List<BalanceDomain>> {
            override fun call(): List<BalanceDomain> {
                val balances = (service.accountService as BitstampAccountServiceRaw).bitstampBalance.balances
                return mapper.map(balances)
            }
        })
    }

    class BalanceMapper {
        fun map(balances: Collection<BitstampBalance.Balance>): List<BalanceDomain> {
            val mutableListOf = mutableListOf<BalanceDomain>()
            for (xbalance in balances) {
                mutableListOf.add(BalanceDomain(
                        null,
                        CurrencyCode.lookup(xbalance.currency),
                        xbalance.balance,
                        xbalance.available,
                        xbalance.reserved))
            }
            return mutableListOf.toList()
        }
    }
}