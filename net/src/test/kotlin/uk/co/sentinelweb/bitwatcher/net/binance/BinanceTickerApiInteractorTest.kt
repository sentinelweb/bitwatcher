package uk.co.sentinelweb.bitwatcher.net.binance

import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.binance.BinanceExchange
import org.knowm.xchange.binance.service.BinanceMarketDataServiceRaw
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class BinanceTickerApiInteractorTest {

    @Test
    fun getTicker_binance() {
        val sut = BinanceTickerDataApiInteractor()

        val tickerObservable = sut.getTicker(CurrencyCode.IOTA, CurrencyCode.BTC)

        val testObserver = TestObserver<TickerDomain>()
        tickerObservable.subscribe(testObserver)

        Assert.assertThat(testObserver.events.size, CoreMatchers.`is`(3))
        val actual = testObserver.events.get(0).get(0) as TickerDomain
        assertNotNull(actual)
        assertEquals( CurrencyCode.IOTA, actual.currencyCode)
        assertEquals( CurrencyCode.BTC, actual.baseCurrencyCode)
    }


    @Test
    fun getTicker_binanceRawIotaBtc() {
        val binanceMarketDataServiceRaw = ExchangeFactory.INSTANCE.createExchange(BinanceExchange::class.java.name).marketDataService as BinanceMarketDataServiceRaw

        val ticker24h = binanceMarketDataServiceRaw.ticker24h("IOTABTC")
        assertNotNull(ticker24h)

    }


}