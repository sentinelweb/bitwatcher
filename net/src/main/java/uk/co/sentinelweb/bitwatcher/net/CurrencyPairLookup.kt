package uk.co.sentinelweb.bitwatcher.net

import org.knowm.xchange.currency.CurrencyPair
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.CurrencyPair.Companion.getKey

class CurrencyPairLookup {
    companion object {
        private val LOOKUP: Map<String, CurrencyPair> = mapOf(
                Pair(getKey(CurrencyCode.BTC , CurrencyCode.USD), CurrencyPair.BTC_USD),
                Pair(getKey(CurrencyCode.BTC , CurrencyCode.EUR), CurrencyPair.BTC_EUR),
                Pair(getKey(CurrencyCode.BTC , CurrencyCode.GBP), CurrencyPair.BTC_GBP),
                Pair(getKey(CurrencyCode.ETH , CurrencyCode.USD), CurrencyPair.ETH_USD),
                Pair(getKey(CurrencyCode.ETH , CurrencyCode.EUR), CurrencyPair.ETH_EUR),
                Pair(getKey(CurrencyCode.ETH , CurrencyCode.GBP), CurrencyPair.ETH_GBP),
                Pair(getKey(CurrencyCode.BCH , CurrencyCode.USD), CurrencyPair.BCH_USD),
                Pair(getKey(CurrencyCode.BCH , CurrencyCode.EUR), CurrencyPair.BCH_EUR),
                Pair(getKey(CurrencyCode.BCH , CurrencyCode.GBP), CurrencyPair.BCH_GBP),
                Pair(getKey(CurrencyCode.XRP , CurrencyCode.USD), CurrencyPair.XRP_USD),
                Pair(getKey(CurrencyCode.XRP , CurrencyCode.EUR), CurrencyPair.XRP_EUR),
                Pair(getKey(CurrencyCode.IOTA , CurrencyCode.BTC), CurrencyPair.IOTA_BTC),
                Pair(getKey(CurrencyCode.IOTA , CurrencyCode.USD), CurrencyPair.IOTA_USD)
        )

        fun lookup(cur:CurrencyCode, base:CurrencyCode) : CurrencyPair? {
            return LOOKUP.get(getKey(cur,base))
        }


    }


}