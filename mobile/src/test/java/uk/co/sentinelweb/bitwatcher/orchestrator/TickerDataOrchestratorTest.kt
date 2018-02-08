package uk.co.sentinelweb.bitwatcher.orchestrator

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.annotations.Fixture
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.dao.TickerDao
import uk.co.sentinelweb.bitwatcher.common.database.entities.TickerEntity
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor
import uk.co.sentinelweb.domain.TickerDomain

class TickerDataOrchestratorTest {
    @Mock lateinit var mockDb: BitwatcherDatabase
    @Mock lateinit var mockTickerDao: TickerDao
    @Mock lateinit var mockTickersInteractor: TickerMergeInteractor
    @Mock lateinit var mockTickerEntityMapper: TickerDomainToEntityMapper
    @Mock lateinit var mockTickerDomainMapper: TickerEntityToDomainMapper

    lateinit var sut: TickerDataOrchestrator

    @Fixture lateinit var tickerEntity: TickerEntity
    @Fixture lateinit var previousTickerEntity: TickerEntity
    @Fixture lateinit var tickerDomain: TickerDomain

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        FixtureAnnotations.initFixtures(this)

        sut = TickerDataOrchestrator(mockTickersInteractor, mockDb, mockTickerEntityMapper, mockTickerDomainMapper)
    }

    @Test
    fun downloadTickerToRepository_hasCurrentAndPrevious() {
        whenever(mockTickersInteractor.getMergedTickers()).thenReturn(Observable.just(tickerDomain))
        whenever(mockDb.tickerDao()).thenReturn(mockTickerDao)
        whenever(mockTickerDao.loadTickerNamed(tickerDomain.currencyCode, tickerDomain.baseCurrencyCode, TickerDomain.NAME_CURRENT))
                .thenReturn(tickerEntity)
        whenever(mockTickerDao.loadTickerNamed(tickerDomain.currencyCode, tickerDomain.baseCurrencyCode, TickerDomain.NAME_PREVIOUS))
                .thenReturn(previousTickerEntity)
        whenever(mockTickerDomainMapper.map(tickerEntity)).thenReturn(tickerDomain)
        whenever(mockTickerEntityMapper.map(tickerDomain)).thenReturn(tickerEntity)

        val testObserver = TestObserver<TickerDomain>()

        sut.downloadTickerToRepository().subscribe(testObserver)

        verify(mockTickerDao).updateTicker(
                tickerDomain.currencyCode,
                tickerDomain.baseCurrencyCode,
                TickerDomain.NAME_CURRENT,
                tickerDomain.last,
                tickerDomain.from)

        verify(mockTickerDao).updateTicker(
                tickerDomain.currencyCode,
                tickerDomain.baseCurrencyCode,
                TickerDomain.NAME_PREVIOUS,
                tickerEntity.amount,
                tickerEntity.dateStamp)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.values().get(0) shouldEqual tickerDomain
    }

    @Test
    fun observeTickersFromRepository() {

    }

}