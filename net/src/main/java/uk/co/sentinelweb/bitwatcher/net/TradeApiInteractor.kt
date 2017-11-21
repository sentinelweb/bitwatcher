package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.Single
import org.knowm.xchange.bitstamp.service.BitstampTradeHistoryParams
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.dto.Order
import org.knowm.xchange.dto.trade.UserTrade
import uk.co.sentinelweb.bitwatcher.domain.Trade
import uk.co.sentinelweb.bitwatcher.domain.TradeType
import uk.co.sentinelweb.bitwatcher.domain.Transaction
import java.time.Instant
import java.util.concurrent.Callable

class TradeApiInteractor(val mapper: TradesMapper = TradesMapper()) {

    fun getUserTrades(exProvider:ExchangeProvider):Single<List<Trade>> {
        return Single.fromCallable(object : Callable<List<Trade>> {
            override fun call(): List<Trade> {
                System.err.println("getting trades ...")
                val bitstampTradeHistoryParams = BitstampTradeHistoryParams(CurrencyPair.BTC_USD, 100)
                bitstampTradeHistoryParams.pageNumber = 0
                val trades = exProvider.exchange.tradeService.getTradeHistory(bitstampTradeHistoryParams)
                System.err.println("got trades:${trades.userTrades.size}")
                return mapper.map(trades.userTrades.toList())
            }
        })
    }


    class TradesMapper() {
        fun map(trades:List<UserTrade>) : List<Trade> {
            val result = mutableListOf<Trade>()
            trades.forEach {
                val type = mapTradeType(it.type)
                if (type != TradeType.UNKNOWN) {
                    result.add(Trade(
                            it.timestamp,
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

        fun mapTradeType(t:Order.OrderType): TradeType {
            when (t){
                Order.OrderType.BID -> return TradeType.BID
                Order.OrderType.ASK -> return TradeType.ASK
            }
            return TradeType.UNKNOWN
        }
    }
}