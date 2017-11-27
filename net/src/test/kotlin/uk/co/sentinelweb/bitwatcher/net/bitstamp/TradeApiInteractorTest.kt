package uk.co.sentinelweb.bitwatcher.net.bitstamp

import io.reactivex.observers.TestObserver
import org.hamcrest.core.Is
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.domain.Trade
import uk.co.sentinelweb.bitwatcher.net.ExchangeDataProvider

class TradeApiInteractorTest {
    lateinit var sut: TradeApiInteractor
    lateinit var dataProvider: ExchangeDataProvider
    val observer = TestObserver<List<Trade>>()

    @Before
    fun setUp() {
        val key = System.getProperty("TX_API_KEY")
        val secret = System.getProperty("TX_SECRET")
        val user = System.getProperty("BITSTAMP_USER")

        dataProvider = ExchangeDataProvider(key, secret, user)
        sut = TradeApiInteractor(BitstampService(dataProvider))
    }

    @Test
    fun getTransactions() {
        val transactionsObservable = sut.getUserTrades()
        transactionsObservable
                .subscribe(observer)

        Assert.assertThat(observer.events.size, Is.`is`(IsNot.not(0)))
        Assert.assertThat(observer.events.get(0).size, Is.`is`(IsNot.not(0)))

//        transactionsObservable
//                .subscribe({ list -> list.forEach({ println(it.tid) }) },
//                        { e ->
//                            println(e)
//                            e.printStackTrace(System.err)
//                        },
//                        { println("onComplete") })
    }

}