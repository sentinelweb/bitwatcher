package uk.co.sentinelweb.bitwatcher.net.xchange.generic

import io.reactivex.Observable
import io.reactivex.Single
import uk.co.sentinelweb.bitwatcher.net.TransactionsDataInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService
import uk.co.sentinelweb.bitwatcher.net.xchange.mapper.TransactionMapper
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain
import java.util.concurrent.Callable

class TransactionApiInteractor(
        private val service: ExchangeService,
        private val mapper: TransactionMapper = TransactionMapper()
) : TransactionsDataInteractor {

    override fun getTransactionsForCurrencies(currencies: List<CurrencyCode>): Observable<List<TransactionDomain>> {
        val currencyObservables = mutableListOf<Observable<List<TransactionDomain>>>()
        currencies.forEach({code -> currencyObservables.add(getTransactionsforCurrency(code).toObservable())})
        return Observable.mergeDelayError(currencyObservables)
    }

    private fun getTransactionsforCurrency(currency:CurrencyCode): Single<List<TransactionDomain>> {
        return Single.fromCallable(object : Callable<List<TransactionDomain>> {
            override fun call(): List<TransactionDomain> {
                System.err.println("getting transactions ...")
                val params = service.accountService.createFundingHistoryParams()
                val trades = service.accountService.getFundingHistory(params)
                System.err.println("got transactions: ${trades.size}")
                return mapper.map(trades)
            }
        })
    }

    override fun getTransactions(): Single<List<TransactionDomain>> {
        return Single.fromCallable(object : Callable<List<TransactionDomain>> {
            override fun call(): List<TransactionDomain> {
                System.err.println("getting transactions ...")
                val params = service.accountService.createFundingHistoryParams()
                val trades = service.accountService.getFundingHistory(params)
                System.err.println("got transactions:${trades.size}")
                return mapper.map(trades)
            }
        })
    }

}