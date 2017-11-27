package uk.co.sentinelweb.bitwatcher.net.coinfloor

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.coinfloor.CoinfloorExchange
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.TradeService
import uk.co.sentinelweb.bitwatcher.net.ExchangeDataProvider
import uk.co.sentinelweb.bitwatcher.net.ExchangeService

class CoinfloorService(val dataProvider: ExchangeDataProvider) : ExchangeService {
    companion object {
        val GUEST: uk.co.sentinelweb.bitwatcher.net.coinfloor.CoinfloorService = CoinfloorService(ExchangeDataProvider.GUEST_DATA_PROVIDER)
    }

    override val marketDataService = ExchangeFactory.INSTANCE.createExchange(CoinfloorExchange::class.java.name).marketDataService

    override val accountService: AccountService
        get() = dataProvider.exchange.accountService

    override val tradeService: TradeService
        get() = dataProvider.exchange.tradeService
}