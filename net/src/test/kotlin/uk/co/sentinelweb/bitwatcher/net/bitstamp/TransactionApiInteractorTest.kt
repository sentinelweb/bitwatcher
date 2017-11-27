package uk.co.sentinelweb.bitwatcher.net.bitstamp

import io.reactivex.observers.TestObserver
import org.hamcrest.core.Is
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.domain.Transaction
import uk.co.sentinelweb.bitwatcher.net.ExchangeDataProvider

class TransactionApiInteractorTest {
    lateinit var sut: TransactionApiInteractor
    lateinit var dataProvider: ExchangeDataProvider
    val observer = TestObserver<List<Transaction>>()

    @Before
    fun setUp() {
        val key = System.getProperty("TX_API_KEY")
        val secret = System.getProperty("TX_SECRET")
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