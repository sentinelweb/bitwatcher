package uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp

import io.reactivex.observers.TestObserver
import org.hamcrest.core.Is
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeDataProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.generic.TransactionApiInteractor
import uk.co.sentinelweb.domain.TransactionDomain

class TransactionApiInteractorTest {
    lateinit var sut: TransactionApiInteractor
    lateinit var dataProvider: ExchangeDataProvider
    val observer = TestObserver<List<TransactionDomain>>()

    @Before
    fun setUp() {
        val key = System.getProperty("BITSTAMP_API_KEY")
        val secret = System.getProperty("BITSTAMP_SECRET")
        val user = System.getProperty("BITSTAMP_USER")


        dataProvider = ExchangeDataProvider(key, secret, user)
        sut = TransactionApiInteractor(BitstampService(dataProvider))
    }

    @Test
    fun getTransactions() {
        val transactionsObservable = sut.getTransactions()
        transactionsObservable
                .subscribe(observer)

        Assert.assertThat(observer.events.size, Is.`is`(IsNot.not(0)))
        Assert.assertThat(observer.events.get(0).size, Is.`is`(IsNot.not(0)))
    }

}