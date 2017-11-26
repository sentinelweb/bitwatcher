package uk.co.sentinelweb.bitwatcher.net

import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.bitstamp.BitstampExchange
import org.knowm.xchange.bitstamp.service.BitstampAccountService
import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.service.trade.TradeService

val GUEST:BitstampService = BitstampService(GUEST_PROVIDER)

class BitstampService (val provider : ExchangeProvider){

    val marketDataService = ExchangeFactory.INSTANCE.createExchange(BitstampExchange::class.java.name).marketDataService

    val accountService:AccountService
        get() = provider.exchange.accountService

    val tradeService:TradeService
            get() = provider.exchange.tradeService
}