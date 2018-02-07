package uk.co.sentinelweb.bitwatcher.orchestrator

import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor

class TickerDataOrchestratorTest {
    @Mock lateinit var mockDb: BitwatcherDatabase
    @Mock lateinit var mockTickersInteractor: TickerMergeInteractor
    @Mock lateinit var mockTickerEntityMapper: TickerDomainToEntityMapper
    @Mock lateinit var mockTickerDomainMapper: TickerEntityToDomainMapper

    lateinit var sut: TickerDataOrchestrator

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = TickerDataOrchestrator(mockTickersInteractor, mockDb, mockTickerEntityMapper, mockTickerDomainMapper)
    }

    @Test
    fun downloadTickerToRepository() {
    }

    @Test
    fun observeTickersFromRepository() {
    }

}