package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Observable
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance
import org.knowm.xchange.bitstamp.service.BitstampAccountServiceRaw
import uk.co.sentinelweb.bitwatcher.domain.Balance
import java.util.concurrent.Callable

class BalanceApiInteractor(val mapper:BalanceMapper = BalanceMapper()) {

    fun getAccountBalance(provider : ExchangeProvider): Observable<List<Balance>> {
        return Observable.fromCallable(object : Callable<List<Balance>> {
            override fun call(): List<Balance> {
                val accountService = provider.exchange.getAccountService()

                val balances = (accountService as BitstampAccountServiceRaw).bitstampBalance.balances
                return mapper.map(balances)
            }
        })
    }

    class BalanceMapper {
        fun map(balances: MutableCollection<BitstampBalance.Balance>): List<Balance> {
            val mutableListOf = mutableListOf<Balance>()
            for (xbalance in balances) {
                mutableListOf.add(Balance(xbalance.currency, xbalance.balance, xbalance.available, xbalance.reserved))
            }
            return mutableListOf.toList()
        }
    }
}