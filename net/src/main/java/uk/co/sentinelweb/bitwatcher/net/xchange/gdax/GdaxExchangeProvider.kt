package uk.co.sentinelweb.bitwatcher.net.xchange.gdax

import org.knowm.xchange.Exchange
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.gdax.GDAXExchange
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider

class GdaxExchangeProvider(private val key: String, private val secret: String, private val userName: String) : ExchangeProvider() {

    override fun createExchange(): Exchange {
        val exSpec = GDAXExchange().getDefaultExchangeSpecification()
        exSpec.setUserName(userName)
        exSpec.setApiKey(key)
        exSpec.setSecretKey(secret)
        val bitstamp = ExchangeFactory.INSTANCE.createExchange(exSpec)
        return bitstamp
    }

    override fun createGuestExchange(): Exchange {
        return ExchangeFactory.INSTANCE.createExchange(GDAXExchange().getDefaultExchangeSpecification())
    }
}