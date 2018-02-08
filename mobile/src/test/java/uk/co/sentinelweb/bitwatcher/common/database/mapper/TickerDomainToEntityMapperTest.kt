package uk.co.sentinelweb.bitwatcher.common.database.mapper

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.annotations.Fixture
import org.amshove.kluent.shouldEqual
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import uk.co.sentinelweb.domain.TickerDomain

class TickerDomainToEntityMapperTest {

    @Fixture
    lateinit var domain: TickerDomain

    lateinit var sut: TickerDomainToEntityMapper
    @Before
    fun setUp() {
        FixtureAnnotations.initFixtures(this)

        sut = TickerDomainToEntityMapper()
    }

    @Test
    fun map() {

        val actual = sut.map(domain)

        actual.id shouldEqual 0
        actual.amount shouldEqual domain.last
        actual.baseCode shouldEqual domain.baseCurrencyCode
        actual.currencyCode shouldEqual domain.currencyCode
        actual.dateStamp shouldEqual domain.from
        actual.name shouldEqual domain.name
    }

}