package uk.co.sentinelweb.bitwatcher.net

import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import org.knowm.xchange.currency.CurrencyPair

class ExchangeProviderTest {

    lateinit var sut: ExchangeProvider

//    private val key: String = mock()
//    private val secret: String = mock()
//    private val user: String = mock()

    @Before
    fun setUp() {
        val key = System.getProperty("BAL_API_KEY")
        val secret = System.getProperty("BAL_SECRET")
        val user = System.getProperty("BITSTAMP_USER")

        sut = ExchangeProvider(key, secret, user)
    }

    @Test
    fun getExchnage() {
        sut.exchange.marketDataService.getTicker(CurrencyPair.BTC_USD);

    }

}