package uk.co.sentinelweb.bitwatcher.net.xchange.coinfloor

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.coinfloor.CoinfloorExchange
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.TradeService
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService

class CoinfloorService(val provider: ExchangeProvider) : ExchangeService {
    companion object {
        val GUEST: uk.co.sentinelweb.bitwatcher.net.xchange.coinfloor.CoinfloorService = CoinfloorService(CoinfloorExchangeProvider("","",""))
    }

    override val marketDataService = ExchangeFactory.INSTANCE.createExchange(CoinfloorExchange::class.java.name).marketDataService

    override val accountService: AccountService
        get() = provider.userExchange.accountService

    override val tradeService: TradeService
        get() = provider.userExchange.tradeService
}