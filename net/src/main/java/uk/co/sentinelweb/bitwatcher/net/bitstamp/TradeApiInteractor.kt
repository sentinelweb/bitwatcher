package uk.co.sentinelweb.bitwatcher.net.bitstamp

import io.reactivex.Single
import org.knowm.xchange.bitstamp.service.BitstampTradeHistoryParams
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.dto.Order
import org.knowm.xchange.dto.trade.UserTrade
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.Trade
import uk.co.sentinelweb.bitwatcher.domain.Trade.Companion.TradeType.*
import java.util.concurrent.Callable

class TradeApiInteractor(private val service: BitstampService, private val mapper: TradesMapper = TradesMapper()) {

    fun getUserTrades():Single<List<Trade>> {
        return Single.fromCallable(object : Callable<List<Trade>> {
            override fun call(): List<Trade> {
                System.err.println("getting trades ...")
                val bitstampTradeHistoryParams = BitstampTradeHistoryParams(CurrencyPair.BTC_USD, 100)
                bitstampTradeHistoryParams.pageNumber = 0
                val trades = service.tradeService.getTradeHistory(bitstampTradeHistoryParams)
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
                if (type != UNKNOWN) {
                    result.add(Trade(
                            it.timestamp,
                            it.id,
                            type,
                            it.originalAmount,
                            it.price,
                            CurrencyCode.lookup(it.currencyPair.base.currencyCode)!!,
                            CurrencyCode.lookup(it.currencyPair.counter.currencyCode)!!,
                            it.feeAmount,
                            it.feeCurrency.currencyCode
                    ))
                }
            }
            return result.toList()
        }

        fun mapTradeType(t:Order.OrderType): Trade.Companion.TradeType {
            when (t){
                Order.OrderType.BID -> return BID
                Order.OrderType.ASK -> return ASK
                else -> return UNKNOWN
            }
        }
    }
}