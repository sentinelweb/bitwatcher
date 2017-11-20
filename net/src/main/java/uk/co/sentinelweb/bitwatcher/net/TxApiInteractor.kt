package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Observable
import io.reactivex.Single
import org.knowm.xchange.bitstamp.service.BitstampTradeHistoryParams
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.dto.Order
import org.knowm.xchange.dto.trade.UserTrade
import uk.co.sentinelweb.bitwatcher.domain.OrderType
import uk.co.sentinelweb.bitwatcher.domain.Transaction
import java.time.Instant
import java.util.concurrent.Callable

class TxApiInteractor(val mapper:TxMapper = TxMapper()) {

    fun getTransactions(exProvider:ExchangeProvider):Single<List<Transaction>> {
        return Single.fromCallable(object : Callable<List<Transaction>> {
            override fun call(): List<Transaction> {
                System.err.println("getting trades ...")
                val bitstampTradeHistoryParams = BitstampTradeHistoryParams(CurrencyPair.BTC_USD, 100)
                bitstampTradeHistoryParams.pageNumber = 0
                val trades = exProvider.exchange.tradeService.getTradeHistory(bitstampTradeHistoryParams)
                System.err.println("got trades:${trades.userTrades.size}")
                return mapper.map(trades.userTrades.toList())
            }
        })
    }


    class TxMapper() {
        fun map(trades:List<UserTrade>) : List<Transaction> {
            val result = mutableListOf<Transaction>()
            trades.forEach {
                val type = mapOrderType(it.type)
                if (type != OrderType.UNKNOWN) {
                    result.add(Transaction(
                            Instant.ofEpochMilli(it.timestamp.time),
                            it.id,
                            type,
                            it.originalAmount,
                            it.price,
                            it.currencyPair.base.currencyCode,
                            it.currencyPair.counter.currencyCode,
                            it.feeAmount,
                            it.feeCurrency.currencyCode
                    ))
                }
            }
            return result.toList()
        }

        fun mapOrderType (t:Order.OrderType):OrderType {
            when (t){
                Order.OrderType.BID -> return OrderType.BUY
                Order.OrderType.ASK -> return OrderType.SELL
            }
            return OrderType.UNKNOWN
        }
    }
}