package uk.co.sentinelweb.domain

class CurrencyPair constructor(val currency:CurrencyCode, val base: CurrencyCode) {
    companion object {
        fun getKey(cur:CurrencyCode, base:CurrencyCode) : String {
            return cur.toString() + base.toString()
        }
    }

    fun getKey():String{
        return Companion.getKey(currency, base)
    }
}