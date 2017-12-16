package uk.co.sentinelweb.domain.mappers

import uk.co.sentinelweb.domain.CurrencyCode

class CurrencyListGenerator {
    companion object {
        fun getCurrencyList():Array<String> {
            val currencyCodesToDisplay = mutableListOf<CurrencyCode>()
            CurrencyCode.values().forEachIndexed { _, code ->
                if (code != CurrencyCode.NONE && code != CurrencyCode.UNKNOWN) currencyCodesToDisplay.add(code)
            }
            val currencyDisplayString = arrayOfNulls<String>(currencyCodesToDisplay.size) // TODO init array nonNull
            currencyCodesToDisplay.forEachIndexed({ i, code -> currencyDisplayString[i] = code.toString() })
            return currencyDisplayString as Array<String>
        }
    }
}