package uk.co.sentinelweb.bitwatcher.orchestrator

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.JFixture
import com.flextrade.jfixture.SpecimenSupplier
import com.flextrade.jfixture.annotations.Fixture
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.MaybeObserver
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.maybe.MaybeObserveOn
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.sentinelweb.bitwatcher.R.id.account
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TickerRateInteractor
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TradeDatabaseInteractor
import uk.co.sentinelweb.bitwatcher.net.TradeDataInteractor
import uk.co.sentinelweb.domain.*
import uk.co.sentinelweb.domain.TransactionItemDomain.TradeDomain
import java.math.BigDecimal
import java.util.*

class TradeOrchestratorTest {

    @Mock lateinit var mockBsTradesInteractor: TradeDataInteractor;
    @Mock lateinit var mockBnTradesInteractor: TradeDataInteractor;
    @Mock lateinit var mockTradeInteractor: TradeDatabaseInteractor;
    @Mock lateinit var mockAccountInteractor: AccountInteractor;
    @Mock lateinit var mockTickerRateInteractor: TickerRateInteractor;

//    @Fixture lateinit var account: AccountDomain
//    @Fixture lateinit var trade: TransactionItemDomain.TradeDomain

    lateinit var sut: TradeOrchestrator

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
//        val jFixture = JFixture()
//        jFixture.customise().useSubType(TransactionItemDomain::class.java, TransactionItemDomain.TradeDomain::class.java)
//        FixtureAnnotations.initFixtures(this)

        sut = TradeOrchestrator(mockBsTradesInteractor, mockBnTradesInteractor, mockTradeInteractor,
                mockAccountInteractor, mockTickerRateInteractor)
    }

    @Test
    fun checkOpenTrade() {
        val account = AccountDomain(1, "test", AccountType.GHOST, listOf(), listOf(), ColourDomain.RED)
        val trade = TradeDomain("tid", Date(), BigDecimal("2.33"), CurrencyCode.BTC,
                TradeDomain.TradeType.BID, BigDecimal("6000"),CurrencyCode.USD, BigDecimal(".0025"), CurrencyCode.USD, TradeDomain.TradeStatus.INITIAL)
        val testObserver = TestObserver<TradeDomain>()
        whenever(mockTickerRateInteractor.getRate(trade.currencyCodeTo, trade.currencyCodeFrom)).thenReturn(Single.just(BigDecimal(6000)))
        sut.checkOpenTrade(account, trade).subscribe(testObserver)
    }

}