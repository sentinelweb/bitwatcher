package uk.co.sentinelweb.bitwatcher.net.xchange.binance

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.binance.BinanceExchange
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.TradeService
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService

class BinanceService(val provider: ExchangeProvider) : ExchangeService {

    override val marketDataService = ExchangeFactory.INSTANCE.createExchange(BinanceExchange::class.java.name).marketDataService

    override val accountService: AccountService
        get() = provider.userExchange.accountService

    override val tradeService: TradeService
        get() = provider.userExchange.tradeService
}