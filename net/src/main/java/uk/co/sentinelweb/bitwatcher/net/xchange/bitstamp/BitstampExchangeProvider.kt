package uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp

import org.knowm.xchange.Exchange
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.bitstamp.BitstampExchange
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider

class BitstampExchangeProvider(private val key: String, private val secret: String, private val userName: String) : ExchangeProvider() {

    override fun createExchange(): Exchange {
        //System.out.println("bitstamp use details : ${userExchange} : ${key} : ${secret}")
        val exSpec = BitstampExchange().getDefaultExchangeSpecification()
        exSpec.setUserName(userName)
        exSpec.setApiKey(key)
        exSpec.setSecretKey(secret)
        val bitstamp = ExchangeFactory.INSTANCE.createExchange(exSpec)
        return bitstamp
    }

    override fun createGuestExchange(): Exchange {
        return ExchangeFactory.INSTANCE.createExchange(BitstampExchange().getDefaultExchangeSpecification())
    }
}