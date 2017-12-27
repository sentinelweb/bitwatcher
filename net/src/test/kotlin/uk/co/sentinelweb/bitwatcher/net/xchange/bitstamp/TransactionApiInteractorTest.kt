package uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp

import io.reactivex.observers.TestObserver
import org.hamcrest.core.Is
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.generic.TransactionApiInteractor
import uk.co.sentinelweb.domain.TransactionItemDomain

class TransactionApiInteractorTest {
    lateinit var sut: TransactionApiInteractor
    lateinit var provider: ExchangeProvider
    val observer = TestObserver<List<TransactionItemDomain.TransactionDomain>>()

    @Before
    fun setUp() {
        provider = BitstampExchangeProvider(BitstampUserData.key, BitstampUserData.secret, BitstampUserData.user)
        sut = TransactionApiInteractor(BitstampService(provider))
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