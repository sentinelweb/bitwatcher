package uk.co.sentinelweb.bitwatcher.net

import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.CoreMatchers
import org.hamcrest.core.Is
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.domain.Transaction

class TxApiInteractorTest {
    lateinit var sut: TxApiInteractor
    lateinit var provider: ExchangeProvider
    val observer = TestObserver<List<Transaction>>()

    @Before
    fun setUp() {
        val key = System.getProperty("TX_API_KEY")
        val secret = System.getProperty("TX_SECRET")
        val user = System.getProperty("BITSTAMP_USER")

        provider = ExchangeProvider(key, secret, user)
        sut = TxApiInteractor()
    }

    @Test
    fun getTransactions() {
        val transactionsObservable = sut.getTransactions(provider)
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