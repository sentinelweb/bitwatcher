package uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp

import io.reactivex.observers.TestObserver
import org.hamcrest.core.Is
import org.hamcrest.core.IsNot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.generic.TradeApiInteractor
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.domain.TransactionItemDomain

class TradeApiInteractorTest {
    lateinit var sut: TradeApiInteractor
    lateinit var provider: ExchangeProvider
    val observer = TestObserver<List<TransactionItemDomain.TradeDomain>>()

    @Before
    fun setUp() {
        provider = BitstampExchangeProvider(BitstampUserData.key, BitstampUserData.secret, BitstampUserData.user)
        sut = TradeApiInteractor(BitstampService(provider), paramsProvider = BitstampTradeHistoryParamsProvider())
    }

    @Test
    fun getTransactions() {
        val transactionsObservable = sut.getUserTradesForPair(CurrencyPair(CurrencyCode.BTC, CurrencyCode.USD))
        transactionsObservable
                .subscribe(observer)

        Assert.assertThat(observer.events.size, Is.`is`(IsNot.not(0)))
        Assert.assertThat(observer.events.get(0).size, Is.`is`(IsNot.not(0)))

    }

}