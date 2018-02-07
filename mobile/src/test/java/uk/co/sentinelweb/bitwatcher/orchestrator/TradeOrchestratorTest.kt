package uk.co.sentinelweb.bitwatcher.orchestrator

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.sentinelweb.bitwatcher.testutils.any
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TickerRateInteractor
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TradeDatabaseInteractor
import uk.co.sentinelweb.bitwatcher.net.TradeDataInteractor
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.ColourDomain
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import java.math.BigDecimal
import java.util.*

class TradeOrchestratorTest {

    @Mock lateinit var mockBsTradesInteractor: TradeDataInteractor
    @Mock lateinit var mockBnTradesInteractor: TradeDataInteractor
    @Mock lateinit var mockTradeInteractor: TradeDatabaseInteractor
    @Mock lateinit var mockAccountInteractor: AccountInteractor
    @Mock lateinit var mockTickerRateInteractor: TickerRateInteractor

    val account = AccountDomain(1,
            "test",
            AccountType.GHOST,
            listOf(),
            listOf(),
            ColourDomain.RED)

    val trade = TradeDomain("tid",
            Date(),
            BigDecimal(2.33),
            CurrencyCode.BTC,
            TradeDomain.TradeType.BID,
            BigDecimal(6000),
            CurrencyCode.USD,
            BigDecimal(.0025),
            CurrencyCode.USD,
            TradeDomain.TradeStatus.INITIAL)


    lateinit var sut: TradeOrchestrator

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = TradeOrchestrator(mockBsTradesInteractor, mockBnTradesInteractor, mockTradeInteractor,
                mockAccountInteractor, mockTickerRateInteractor)
    }

    @Test
    fun checkOpenTrade_clears() {
        val testObserver = TestObserver<TradeDomain>()
        whenever(mockTickerRateInteractor.getRateRange(trade.currencyCodeTo, trade.currencyCodeFrom)).thenReturn(Single.just(Pair(BigDecimal(5999), BigDecimal(6001))))
        whenever(mockTradeInteractor.singleInsertOrUpdate(eq(account), any<TradeDomain>())).thenReturn(Single.just(trade.copy(status = TradeDomain.TradeStatus.COMPLETE)))

        sut.checkOpenTrade(account, trade).subscribe(testObserver)

        //testObserver.errors().get(0).printStackTrace()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        //assertThat(testObserver.values().get(0).status, `is`(TradeDomain.TradeStatus.COMPLETE))
        testObserver.values().get(0).status shouldEqual TradeDomain.TradeStatus.COMPLETE
    }

    @Test
    fun checkOpenTrade_doesntClear() {
        val testObserver = TestObserver<TradeDomain>()
        whenever(mockTickerRateInteractor.getRateRange(trade.currencyCodeTo, trade.currencyCodeFrom)).thenReturn(Single.just(Pair(BigDecimal(5997), BigDecimal(5999))))
        whenever(mockTradeInteractor.singleInsertOrUpdate(eq(account), any<TradeDomain>())).thenReturn(Single.just(trade.copy(status = TradeDomain.TradeStatus.COMPLETE)))

        sut.checkOpenTrade(account, trade).subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertNoValues()
    }



}