package uk.co.sentinelweb.bitwatcher.net.xchange.binance

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.binance.BinanceExchange
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.TradeService
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeDataProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService

class BinanceService(val dataProvider: ExchangeDataProvider) : ExchangeService {
    companion object {
        val GUEST: BinanceService = BinanceService(ExchangeDataProvider.GUEST_DATA_PROVIDER)
    }

    override val marketDataService = ExchangeFactory.INSTANCE.createExchange(BinanceExchange::class.java.name).marketDataService

    override val accountService: AccountService
        get() = dataProvider.exchange.accountService

    override val tradeService: TradeService
        get() = dataProvider.exchange.tradeService
}