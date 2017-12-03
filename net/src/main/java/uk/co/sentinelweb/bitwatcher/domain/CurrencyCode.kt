package uk.co.sentinelweb.bitwatcher.domain

enum class CurrencyCode {
    NONE,
    BTC, BCH, ETH,
    USD, GBP, EUR;

    companion object {
        fun lookup(codeString:String):CurrencyCode? {
            for (code in values()) {
                if (codeString.equals(code.toString())) {
                    return code
                }
            }
            return null
        }
    }


}