package uk.co.sentinelweb.bitwatcher.net

import org.knowm.xchange.Exchange
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.bitstamp.BitstampExchange


class ExchangeDataProvider(private val key: String, private val secret: String, private val user: String) {
    companion object {
        val GUEST_DATA_PROVIDER: ExchangeDataProvider = ExchangeDataProvider("","","")
    }
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