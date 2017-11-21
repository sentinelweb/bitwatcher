package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.observers.TestObserver
import org.hamcrest.core.Is
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.domain.Trade
import uk.co.sentinelweb.bitwatcher.domain.Transaction

class TradeApiInteractorTest {
    lateinit var sut: TradeApiInteractor
    lateinit var provider: ExchangeProvider
    val observer = TestObserver<List<Trade>>()

    @Before
    fun setUp() {
        val key = System.getProperty("TX_API_KEY")
        val secret = System.getProperty("TX_SECRET")
        val user = System.getProperty("BITSTAMP_USER")

        provider = ExchangeProvider(key, secret, user)
        sut = TradeApiInteractor()
    }

    @Test
    fun getTransactions() {
        val transactionsObservable = sut.getUserTrades(provider)
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