package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Observable
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance
import org.knowm.xchange.bitstamp.service.BitstampAccountServiceRaw
import uk.co.sentinelweb.bitwatcher.domain.Balance
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.net.bitstamp.BitstampService
import java.util.concurrent.Callable

class BalanceApiInteractor(val service: BitstampService, val mapper:BalanceMapper = BalanceMapper()) {

    fun getAccountBalance(): Observable<List<Balance>> {
        return Observable.fromCallable(object : Callable<List<Balance>> {
            override fun call(): List<Balance> {
                val balances = (service.accountService as BitstampAccountServiceRaw).bitstampBalance.balances
                return mapper.map(balances)
            }
        })
    }

    class BalanceMapper {
        fun map(balances: MutableCollection<BitstampBalance.Balance>): List<Balance> {
            val mutableListOf = mutableListOf<Balance>()
            for (xbalance in balances) {
                mutableListOf.add(Balance(
                        CurrencyCode.lookup(xbalance.currency)!!,
                        xbalance.balance,
                        xbalance.available,
                        xbalance.reserved))
            }
            return mutableListOf.toList()
        }
    }
}