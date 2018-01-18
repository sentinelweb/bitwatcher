package uk.co.sentinelweb.bitwatcher.net.xchange.mapper

import org.knowm.xchange.dto.Order
import org.knowm.xchange.dto.trade.LimitOrder
import org.knowm.xchange.dto.trade.UserTrade
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeStatus
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeStatus.*
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType.ASK
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType.BID
import java.math.BigDecimal

class TradeMapper() {

    fun map(trades: List<UserTrade>): List<TradeDomain> {
        val result = mutableListOf<TradeDomain>()
        trades.forEach {
            val type = mapTradeType(it.type)
            if (type != TradeType.UNKNOWN) {
                result.add(TradeDomain(
                        it.id,
                        it.timestamp,
                        it.originalAmount,
                        CurrencyCode.lookup(it.currencyPair.base.currencyCode),
                        type,
                        it.price,
                        CurrencyCode.lookup(it.currencyPair.counter.currencyCode),
                        it.feeAmount,
                        CurrencyCode.lookup(it.feeCurrency.currencyCode),
                        TradeStatus.COMPLETE
                ))
            }
        }
        return result.toList()
    }

    fun mapTradeType(t: Order.OrderType): TradeDomain.TradeType {
        when (t) {
            Order.OrderType.BID -> return BID
            Order.OrderType.ASK -> return ASK
            else -> return TradeType.UNKNOWN
        }
    }

    fun mapOpen(trades: List<LimitOrder>): List<TradeDomain> {
        val result = mutableListOf<TradeDomain>()
        trades.forEach {
            val type = mapTradeType(it.type)
            if (type != TradeType.UNKNOWN) {
                result.add(TradeDomain(
                        it.id,
                        it.timestamp,
                        it.originalAmount,
                        CurrencyCode.lookup(it.currencyPair.base.currencyCode),
                        type,
                        it.limitPrice,
                        CurrencyCode.lookup(it.currencyPair.counter.currencyCode),
                        BigDecimal.ZERO,
                        CurrencyCode.NONE,
                        mapStatus(it.status)
                ))
            }
        }
        return result.toList()
    }

    private fun mapStatus(status: Order.OrderStatus): TradeDomain.TradeStatus {
        return when (status) {

            Order.OrderStatus.PENDING_NEW -> PENDING
            Order.OrderStatus.NEW -> PLACED
            Order.OrderStatus.PARTIALLY_FILLED ->IN_PROGRESS
            Order.OrderStatus.FILLED -> COMPLETE
            Order.OrderStatus.PENDING_CANCEL -> PENDING
            Order.OrderStatus.CANCELED -> CANCELLED
            Order.OrderStatus.PENDING_REPLACE -> PENDING
            Order.OrderStatus.REPLACED -> CANCELLED
            Order.OrderStatus.STOPPED -> CANCELLED
            Order.OrderStatus.REJECTED -> FAILED
            Order.OrderStatus.EXPIRED -> FAILED
        }
    }
}