package uk.co.sentinelweb.bitwatcher.net

import org.knowm.xchange.Exchange
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.bitstamp.BitstampExchange

val GUEST_PROVIDER:ExchangeProvider = ExchangeProvider("","","")
class ExchangeProvider(val key: String, val secret: String, val user: String) {
    val exchange: Exchange
        get() = createBitstampExchange(key, secret, user)

    private fun createBitstampExchange(key: String, secret: String, user: String): Exchange {
        System.out.println("use deatils : ${user} : ${key} : ${secret}")
        val exSpec = BitstampExchange().getDefaultExchangeSpecification()
        exSpec.setUserName(user)
        exSpec.setApiKey(key)
        exSpec.setSecretKey(secret)
        val bitstamp = ExchangeFactory.INSTANCE.createExchange(exSpec)
        return bitstamp
    }
}