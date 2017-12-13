package uk.co.sentinelweb.bitwatcher.domain

enum class CurrencyCode {
    NONE,
    BTC, BCH, ETH,LTC,XRP,
    USD, GBP, EUR;

    companion object {
        fun lookup(codeString:String):CurrencyCode? {
            for (code in values()) {
                if (codeString.toUpperCase().equals(code.toString().toUpperCase())) {
                    return code
                }
            }
            return null
        }
    }


}