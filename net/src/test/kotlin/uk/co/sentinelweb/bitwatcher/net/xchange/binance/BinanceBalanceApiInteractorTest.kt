package uk.co.sentinelweb.bitwatcher.net.xchange.binance

import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.domain.BalanceDomain

class BinanceBalanceApiInteractorTest {

    val observer = TestObserver<List<BalanceDomain>>()

    lateinit var sut: BinanceBalanceApiInteractor

    @Before
    fun setUp() {
        sut = BinanceBalanceApiInteractor(BinanceService(BinanceExchangeProvider(BinanceUserData.key,BinanceUserData.secret,BinanceUserData.user)))
    }

    @Test
    fun getAccountBalance() {
        sut.getAccountBalance().subscribe(observer)

        Assert.assertThat(observer.events.size, CoreMatchers.`is`(3))
        val actual = observer.events.get(0).get(0) as List<BalanceDomain>
        assertNotNull(actual)
        assertThat(actual, `is`(not(emptyList())))

    }

}