package uk.co.sentinelweb.bitwatcher.net.xchange

import org.junit.Before
import org.junit.Test
import org.knowm.xchange.currency.CurrencyPair

class ExchangeDataProviderTest {

    lateinit var sut: ExchangeDataProvider

//    private val key: String = mock()
//    private val secret: String = mock()
//    private val user: String = mock()

    @Before
    fun setUp() {
        val key = System.getProperty("BITSTAMP_API_KEY")
        val secret = System.getProperty("BITSTAMP_SECRET")
        val user = System.getProperty("BITSTAMP_USER")

        sut = ExchangeDataProvider(key, secret, user)
    }

    @Test
    fun getExchnage() {
        sut.exchange.marketDataService.getTicker(CurrencyPair.BTC_USD)

    }

}