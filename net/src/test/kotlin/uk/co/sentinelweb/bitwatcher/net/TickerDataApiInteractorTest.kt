package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.TickerData
import uk.co.sentinelweb.bitwatcher.net.bitstamp.BitstampService
import uk.co.sentinelweb.bitwatcher.net.coinfloor.CoinfloorService
import kotlin.test.assertNotNull


class TickerDataApiInteractorTest {

    lateinit var sut: TickerDataApiInteractor

    @Before
    fun setUp() {

    }

    @Test
    fun getTicker_coinfloor() {
        val service = CoinfloorService.Companion.GUEST
        sut = TickerDataApiInteractor(service)

        val tickerObservable = sut.getTicker(CurrencyCode.BTC, CurrencyCode.GBP)

        val testObserver = TestObserver<TickerData>()
        tickerObservable.subscribe(testObserver)

        assertThat(testObserver.events.size, `is`(3))
        assertNotNull(testObserver.events.get(0))
    }

    @Test
    fun getTicker_bitstamp() {
        sut = TickerDataApiInteractor(BitstampService.Companion.GUEST)

        val tickerObservable = sut.getTicker(CurrencyCode.BTC, CurrencyCode.USD)

        val testObserver = TestObserver<TickerData>()
        tickerObservable.subscribe(testObserver)

        assertThat(testObserver.events.size, `is`(3))
        assertNotNull(testObserver.events.get(0))
    }

    @Test
    fun getTickers() {
    }

}