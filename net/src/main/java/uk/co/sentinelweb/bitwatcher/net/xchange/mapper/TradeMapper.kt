package uk.co.sentinelweb.bitwatcher.net.xchange.mapper

import org.knowm.xchange.dto.Order
import org.knowm.xchange.dto.trade.UserTrade
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain.TradeType.*

class TradeMapper() {
        fun map(trades: List<UserTrade>): List<TradeDomain> {
            val result = mutableListOf<TradeDomain>()
            trades.forEach {
                val type = mapTradeType(it.type)
                if (type != UNKNOWN) {
                    result.add(TradeDomain(
                            it.id,
                            it.timestamp,
                            it.originalAmount,
                            CurrencyCode.lookup(it.currencyPair.base.currencyCode),
                            type,
                            it.price,
                            CurrencyCode.lookup(it.currencyPair.counter.currencyCode),
                            it.feeAmount,
                            it.feeCurrency.currencyCode
                    ))
                }
            }
            return result.toList()
        }

        fun mapTradeType(t: Order.OrderType): TradeDomain.TradeType {
            when (t) {
                Order.OrderType.BID -> return BID
                Order.OrderType.ASK -> return ASK
                else -> return UNKNOWN
            }
        }
    }