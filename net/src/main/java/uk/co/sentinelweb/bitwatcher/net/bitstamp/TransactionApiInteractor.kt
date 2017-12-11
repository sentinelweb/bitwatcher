package uk.co.sentinelweb.bitwatcher.net.bitstamp

import io.reactivex.Single
import org.knowm.xchange.bitstamp.service.BitstampTradeHistoryParams
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.dto.account.FundingRecord
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.TransactionDomain
import uk.co.sentinelweb.bitwatcher.domain.TransactionDomain.TransactionStatus.*
import uk.co.sentinelweb.bitwatcher.domain.TransactionDomain.TransactionType.DEPOSIT
import uk.co.sentinelweb.bitwatcher.domain.TransactionDomain.TransactionType.WITHDRAWL
import java.util.concurrent.Callable

class TransactionApiInteractor(private val service: BitstampService, private val mapper: TransactonMapper = TransactonMapper()) {

    fun getTransactions(): Single<List<TransactionDomain>> {
        return Single.fromCallable(object : Callable<List<TransactionDomain>> {
            override fun call(): List<TransactionDomain> {
                System.err.println("getting transactions ...")
                val bitstampTradeHistoryParams = BitstampTradeHistoryParams(CurrencyPair.BTC_USD, 100)
                bitstampTradeHistoryParams.pageNumber = 0
                val trades = service.accountService.getFundingHistory(bitstampTradeHistoryParams)
                System.err.println("got transactions:${trades.size}")
                return mapper.map(trades.toList())
            }
        })
    }


    class TransactonMapper() {
        fun map(trades: List<FundingRecord>): List<TransactionDomain> {
            val result = mutableListOf<TransactionDomain>()
            trades.forEach {
                val type = mapOrderType(it.type)
                result.add(TransactionDomain(
                        type,
                        it.date,
                        it.amount,
                        CurrencyCode.lookup(it.currency.currencyCode)!!,
                        it.balance,
                        it.description,
                        mapStatus(it.status),
                        it.fee
                ))
            }
            return result.toList()
        }

        private fun mapStatus(status: FundingRecord.Status?): TransactionDomain.TransactionStatus {
            when (status) {
                FundingRecord.Status.PROCESSING -> return PROCESSING
                FundingRecord.Status.CANCELLED -> return CANCELLED
                FundingRecord.Status.COMPLETE -> return COMPLETE
                FundingRecord.Status.FAILED -> return FAILED
                else -> return UNKNOWN
            }
        }


        fun mapOrderType(t: FundingRecord.Type): TransactionDomain.TransactionType {
            when (t) {
                FundingRecord.Type.DEPOSIT -> return DEPOSIT
                FundingRecord.Type.WITHDRAWAL -> return WITHDRAWL
            }
        }
    }
}