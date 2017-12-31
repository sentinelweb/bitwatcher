package uk.co.sentinelweb.bitwatcher.net.xchange

import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.marketdata.MarketDataService
import org.knowm.xchange.service.trade.TradeService

interface ExchangeService {
    val marketDataService: MarketDataService
    val accountService: AccountService
    val tradeService: TradeService
}