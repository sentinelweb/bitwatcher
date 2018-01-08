package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import android.support.annotation.ColorRes
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class CalculatorState(
        var amount: BigDecimal = ZERO,
        var rate: BigDecimal = ZERO,
        var currencyFrom: CurrencyCode = CurrencyCode.NONE,
        var currencyTo: CurrencyCode = CurrencyCode.NONE,
        var linkToRate: Boolean = true
) {
    var value: BigDecimal = BigDecimal.ZERO

    data class CalculatorModel(
            val amount: String,
            val value: String,
            val rate: String,
            val fromCurrency: String,
            val toCurrency: String,
            @ColorRes val linkColor: Int
    )

    enum class Field {
        NONE, AMOUNT, RATE
    }
}