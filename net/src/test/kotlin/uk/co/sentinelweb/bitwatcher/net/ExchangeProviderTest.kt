package uk.co.sentinelweb.bitwatcher.net

import org.junit.Before
import org.junit.Test
import org.knowm.xchange.currency.CurrencyPair

class ExchangeProviderTest {
    lateinit var sut: ExchangeProvider
    @Before
    fun setUp() {
        val key = System.getProperty("BAL_API_KEY")
        val secret = System.getProperty("BAL_SECRET")
        val user = System.getProperty("BITSTAMP_USER")
        sut = ExchangeProvider(key, secret, user)
    }

    @Test
    fun getExchnage() {
        sut.exchnage.marketDataService.getTicker(CurrencyPair.BTC_USD);

    }

}