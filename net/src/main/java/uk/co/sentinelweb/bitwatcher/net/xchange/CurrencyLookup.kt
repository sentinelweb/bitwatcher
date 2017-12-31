package uk.co.sentinelweb.bitwatcher.net.xchange

import org.knowm.xchange.currency.Currency
import uk.co.sentinelweb.domain.CurrencyCode

class CurrencyLookup {
    companion object {
        private val LOOKUP: Map<CurrencyCode, Currency> = mapOf(
                Pair(CurrencyCode.BTC , Currency.BTC),
                Pair(CurrencyCode.BCH , Currency.BCH),
                Pair(CurrencyCode.ETH , Currency.ETH),
                Pair(CurrencyCode.IOTA , Currency.IOT),
                Pair(CurrencyCode.XRP , Currency.XRP),
                Pair(CurrencyCode.USD , Currency.USD),
                Pair(CurrencyCode.GBP , Currency.GBP),
                Pair(CurrencyCode.EUR , Currency.EUR),
                Pair(CurrencyCode.AUD , Currency.AUD)
        )

        fun lookup(cur:CurrencyCode) : Currency? {
            return LOOKUP.get(cur)
        }
    }


}