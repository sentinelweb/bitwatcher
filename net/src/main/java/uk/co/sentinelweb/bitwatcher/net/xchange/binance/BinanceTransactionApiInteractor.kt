package uk.co.sentinelweb.bitwatcher.net.xchange.binance

import io.reactivex.Observable
import io.reactivex.Single
import org.knowm.xchange.binance.service.BinanceFundingHistoryParams
import uk.co.sentinelweb.bitwatcher.net.TransactionsDataInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.CurrencyLookup
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService
import uk.co.sentinelweb.bitwatcher.net.xchange.mapper.TransactionMapper
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain
import java.util.concurrent.Callable

class BinanceTransactionApiInteractor(
        private val service: ExchangeService,
        private val mapper: TransactionMapper = TransactionMapper()
) : TransactionsDataInteractor {

    override fun getTransactionsForCurrencies(currencies: List<CurrencyCode>): Observable<List<TransactionDomain>> {
        val currencyObservables = mutableListOf<Observable<List<TransactionDomain>>>()
        currencies.forEach({code -> currencyObservables.add(getTransactionsforCurrency(code).toObservable())})
        return Observable.mergeDelayError(currencyObservables)

    }
    // FIXME wait for xchange 4.3.2 for this to work
    private fun getTransactionsforCurrency(currency:CurrencyCode): Single<List<TransactionDomain>> {
        return Single.fromCallable(object : Callable<List<TransactionDomain>> {
            override fun call(): List<TransactionDomain> {
                System.err.println("getting transactions ...")
                val params = BinanceFundingHistoryParams()//service.accountService.createFundingHistoryParams() as BinanceFundingHistoryParams
                params.currency = CurrencyLookup.lookup(currency)
                val trades = service.accountService.getFundingHistory(params)
                System.err.println("got transactions: ${trades.size}")
                return mapper.map(trades)
            }
        })
    }

    override fun getTransactions(): Single<List<TransactionDomain>> {
        return Single.error(IllegalArgumentException("You need to provide a currency"))
    }

}