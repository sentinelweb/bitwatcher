package uk.co.sentinelweb.bitwatcher.domain

class CurrencyPair {
    companion object {
        fun getKey(cur:CurrencyCode, base:CurrencyCode) : String {
            return cur.toString() + base.toString()
        }
    }
}