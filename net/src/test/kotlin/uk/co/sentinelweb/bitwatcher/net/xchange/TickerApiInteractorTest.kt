package uk.co.sentinelweb.bitwatcher.net.xchange

import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp.BitstampService
import uk.co.sentinelweb.bitwatcher.net.xchange.coinfloor.CoinfloorService
import uk.co.sentinelweb.bitwatcher.net.xchange.generic.TickerDataApiInteractor
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain
import kotlin.test.assertNotNull


class TickerApiInteractorTest {

    lateinit var sut: TickerDataApiInteractor

    @Before
    fun setUp() {

    }

    @Test
    fun getTicker_coinfloor() {
        val service = CoinfloorService.Companion.GUEST
        sut = TickerDataApiInteractor(service)

        val tickerObservable = sut.getTicker(CurrencyCode.BTC, CurrencyCode.GBP)

        val testObserver = TestObserver<TickerDomain>()
        tickerObservable.subscribe(testObserver)

        assertThat(testObserver.events.size, `is`(3))
        assertNotNull(testObserver.events.get(0))
    }

    @Test
    fun getTicker_bitstamp() {
        sut = TickerDataApiInteractor(BitstampService.GUEST)

        val tickerObservable = sut.getTicker(CurrencyCode.BTC, CurrencyCode.USD)

        val testObserver = TestObserver<TickerDomain>()
        tickerObservable.subscribe(testObserver)

        assertThat(testObserver.events.size, `is`(3))
        assertNotNull(testObserver.events.get(0))
    }



    @Test
    fun getTickers() {
    }

}