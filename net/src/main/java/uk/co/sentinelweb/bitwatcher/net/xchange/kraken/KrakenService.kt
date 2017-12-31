package uk.co.sentinelweb.bitwatcher.net.xchange.kraken

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.kraken.KrakenExchange
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.TradeService
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService

class KrakenService(val provider: ExchangeProvider) : ExchangeService {
    companion object {
        val GUEST: KrakenService = KrakenService(KrakenExchangeProvider("","",""))
    }

    override val marketDataService = ExchangeFactory.INSTANCE.createExchange(KrakenExchange::class.java.name).marketDataService

    override val accountService: AccountService
        get() = provider.userExchange.accountService

    override val tradeService: TradeService
        get() = provider.userExchange.tradeService
}