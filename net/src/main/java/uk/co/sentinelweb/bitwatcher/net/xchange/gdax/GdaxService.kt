package uk.co.sentinelweb.bitwatcher.net.xchange.gdax

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.gdax.GDAXExchange
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.TradeService
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService

class GdaxService(val provider: ExchangeProvider) : ExchangeService {
    companion object {
        val GUEST: GdaxService = GdaxService(GdaxExchangeProvider("","",""))
    }

    override val marketDataService = ExchangeFactory.INSTANCE.createExchange(GDAXExchange::class.java.name).marketDataService

    override val accountService: AccountService
        get() = provider.userExchange.accountService

    override val tradeService: TradeService
        get() = provider.userExchange.tradeService
}