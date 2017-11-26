package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Single
import org.knowm.xchange.bitstamp.service.BitstampTradeHistoryParams
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.dto.account.FundingRecord
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.Transaction
import uk.co.sentinelweb.bitwatcher.domain.TransactionStatus
import uk.co.sentinelweb.bitwatcher.domain.TransactionType
import java.time.Instant
import java.util.concurrent.Callable

class TransactionApiInteractor(val service:BitstampService,val mapper: TradesMapper = TradesMapper()) {

    fun getTransactions(): Single<List<Transaction>> {
        return Single.fromCallable(object : Callable<List<Transaction>> {
            override fun call(): List<Transaction> {
                System.err.println("getting transactions ...")
                val bitstampTradeHistoryParams = BitstampTradeHistoryParams(CurrencyPair.BTC_USD, 100)
                bitstampTradeHistoryParams.pageNumber = 0
                val trades = service.accountService.getFundingHistory(bitstampTradeHistoryParams)
                System.err.println("got transactions:${trades.size}")
                return mapper.map(trades.toList())
            }
        })
    }


    class TradesMapper() {
        fun map(trades: List<FundingRecord>): List<Transaction> {
            val result = mutableListOf<Transaction>()
            trades.forEach {
                val type = mapOrderType(it.type)
                result.add(Transaction(
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

        private fun mapStatus(status: FundingRecord.Status?): TransactionStatus {
            when (status) {
                FundingRecord.Status.PROCESSING -> return TransactionStatus.PROCESSING
                FundingRecord.Status.CANCELLED -> return TransactionStatus.CANCELLED
                FundingRecord.Status.COMPLETE -> return TransactionStatus.COMPLETE
                FundingRecord.Status.FAILED -> return TransactionStatus.FAILED
                else -> return TransactionStatus.UNKNOWN
            }
        }


        fun mapOrderType(t: FundingRecord.Type): TransactionType {
            when (t) {
                FundingRecord.Type.DEPOSIT -> return TransactionType.DEPOSIT
                FundingRecord.Type.WITHDRAWAL -> return TransactionType.WITHDRAWL
            }
        }
    }
}