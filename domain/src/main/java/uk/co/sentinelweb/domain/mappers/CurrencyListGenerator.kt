package uk.co.sentinelweb.domain.mappers

import uk.co.sentinelweb.domain.CurrencyCode

class CurrencyListGenerator {
    companion object {
        fun getCurrencyArray(includeNone: Boolean = false): Array<String> {
            val currencyCodesToDisplay = mutableListOf<CurrencyCode>()
            CurrencyCode.values().forEachIndexed { _, code ->
                if ((includeNone || code != CurrencyCode.NONE) && code != CurrencyCode.UNKNOWN) currencyCodesToDisplay.add(code)
            }
            val currencyDisplayString = arrayOfNulls<String>(currencyCodesToDisplay.size) // TODO init array nonNull
            currencyCodesToDisplay.forEachIndexed({ i, code -> currencyDisplayString[i] = code.toString() })
            return currencyDisplayString as Array<String>
        }
    }
}