package uk.co.sentinelweb.domain

enum class CurrencyCode(val type:Type) {
    NONE(Type.NONE), UNKNOWN(Type.NONE),

    BTC(Type.CRYPTO), BCH(Type.CRYPTO), ETH(Type.CRYPTO), LTC(Type.CRYPTO), XRP(Type.CRYPTO),
    IOTA(Type.CRYPTO), XLM(Type.CRYPTO), ICON(Type.CRYPTO), ADA(Type.CRYPTO), XRB(Type.CRYPTO),

    USD(Type.FIAT), GBP(Type.FIAT), EUR(Type.FIAT), AUD(Type.FIAT);

    companion object {
        fun lookup(codeString: String): CurrencyCode {
            for (code in values()) {
                if (codeString.toUpperCase().equals(code.toString().toUpperCase())) {
                    return code
                }
            }
            return UNKNOWN
        }
    }

    enum class Type {
        CRYPTO, FIAT, NONE
    }

}