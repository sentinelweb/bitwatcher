package uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.bitstamp.BitstampExchange
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.TradeService
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService

class BitstampService (private val provider: ExchangeProvider): ExchangeService {
    companion object {
        val GUEST: BitstampService = BitstampService(BitstampExchangeProvider("","", ""))
    }

    override val marketDataService = ExchangeFactory.INSTANCE.createExchange(BitstampExchange::class.java.name).marketDataService

    override val accountService:AccountService
        get() = provider.userExchange.accountService

    override val tradeService:TradeService
            get() = provider.userExchange.tradeService
}