package uk.co.sentinelweb.domain

class CurrencyPair {
    companion object {
        fun getKey(cur:CurrencyCode, base:CurrencyCode) : String {
            return cur.toString() + base.toString()
        }
    }
}