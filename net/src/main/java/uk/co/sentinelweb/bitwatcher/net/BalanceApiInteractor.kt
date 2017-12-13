package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Single
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance
import org.knowm.xchange.bitstamp.service.BitstampAccountServiceRaw
import uk.co.sentinelweb.bitwatcher.domain.BalanceDomain
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.net.bitstamp.BitstampService
import java.util.concurrent.Callable

class BalanceApiInteractor(private val service: BitstampService, private val mapper:BalanceMapper = BalanceMapper()) {

    fun getAccountBalance(): Single<List<BalanceDomain>> {
        return Single.fromCallable(object : Callable<List<BalanceDomain>> {
            override fun call(): List<BalanceDomain> {
                val balances = (service.accountService as BitstampAccountServiceRaw).bitstampBalance.balances
                return mapper.map(balances)
            }
        })
    }

    class BalanceMapper {
        fun map(balances: MutableCollection<BitstampBalance.Balance>): List<BalanceDomain> {
            val mutableListOf = mutableListOf<BalanceDomain>()
            for (xbalance in balances) {
                mutableListOf.add(BalanceDomain(
                        null,
                        CurrencyCode.lookup(xbalance.currency)!!,
                        xbalance.balance,
                        xbalance.available,
                        xbalance.reserved))
            }
            return mutableListOf.toList()
        }
    }
}