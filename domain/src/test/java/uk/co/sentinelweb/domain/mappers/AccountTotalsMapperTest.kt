package uk.co.sentinelweb.domain.mappers

import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain
import uk.co.sentinelweb.domain.mc
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

class AccountTotalsMapperTest {

    lateinit var prices: Map<String, TickerDomain>

    val sut = AccountTotalsMapper()

    @Before
    fun setUp() {

    }

    @Test
    fun testMapSameCurrency() {
        // setup
        prices = mapOf()
        val balance = BalanceDomain(-1, CurrencyCode.BTC, BigDecimal(2), BigDecimal(3), BigDecimal(4))

        // test
        val actual = sut.calculateHoldingInBaseCurrency(balance, CurrencyCode.BTC, prices)

        // verify
        assertEquals(BigDecimal(3), actual)
    }

    @Test
    fun testMapWithPairAvailable() {
        // setup
        prices = mapOf(Pair("BTCUSD", TickerDomain("", Date(), BigDecimal(5), CurrencyCode.BTC, CurrencyCode.USD)))
        val balance = BalanceDomain(-1, CurrencyCode.BTC, BigDecimal(2), BigDecimal(3), BigDecimal(4))

        // test
        val actual = sut.calculateHoldingInBaseCurrency(balance, CurrencyCode.USD, prices)

        // verify
        assertEquals(BigDecimal(15), actual)
    }

    @Test
    fun testMapWithReversePairAvailable() {
        // setup
        prices = mapOf(Pair("USDBTC", TickerDomain("", Date(), BigDecimal(5), CurrencyCode.USD, CurrencyCode.BTC)))
        val balance = BalanceDomain(-1, CurrencyCode.USD, BigDecimal(2), BigDecimal(3), BigDecimal(4))

        // test
        val actual = sut.calculateHoldingInBaseCurrency(balance, CurrencyCode.BTC, prices)

        // verify
        assertEquals(BigDecimal(15), actual)
    }

    @Test
    fun testMapTwoFiatConversionCryptoBaseAvailable() {
        // setup
        prices = mapOf(
                Pair("BTCAUD", TickerDomain("", Date(), BigDecimal(5, mc), CurrencyCode.BTC, CurrencyCode.AUD)),
                Pair("BTCGBP", TickerDomain("", Date(), BigDecimal(6, mc), CurrencyCode.BTC, CurrencyCode.GBP))
        )
        val balance = BalanceDomain(-1, CurrencyCode.AUD, BigDecimal(2, mc), BigDecimal(3, mc), BigDecimal(4, mc))

        // test
        val actual = sut.calculateHoldingInBaseCurrency(balance, CurrencyCode.GBP, prices)

        // verify
        val audbtc = BigDecimal.ONE.divide(BigDecimal(5.0, mc),mc)
        val gbpbtc = BigDecimal.ONE.divide(BigDecimal(6.0, mc),mc)
        assertEquals(gbpbtc.divide(audbtc, mc) * BigDecimal(3, mc), actual)
    }

    @Test
    fun testMapTwoCryptoConversionFiatBaseAvailable() {
        // setup
        prices = mapOf(
                Pair("BTCUSD", TickerDomain("", Date(), BigDecimal(5, mc), CurrencyCode.BTC, CurrencyCode.USD)),
                Pair("BCHUSD", TickerDomain("", Date(), BigDecimal(6, mc), CurrencyCode.BCH, CurrencyCode.USD))
        )
        val balance = BalanceDomain(-1, CurrencyCode.BTC, BigDecimal(2, mc), BigDecimal(3, mc), BigDecimal(4, mc))

        // test
        val actual = sut.calculateHoldingInBaseCurrency(balance, CurrencyCode.BCH, prices)

        // verify

        // TODO a small precision error here examin how to remove it
        val bchbtc = BigDecimal(5, mc).divide(BigDecimal(6, mc), mc);
        Assert.assertThat(actual, Matchers.closeTo(bchbtc * BigDecimal(3, mc), BigDecimal(0.000000001)))

//        val expected: Any = BigDecimal(6, mc).divide(BigDecimal(5, mc), mc) * BigDecimal(3, mc)
//        Assert.assertThat(actual, `is`(expected))
    }

}