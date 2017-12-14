package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import android.support.annotation.ColorRes
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import java.math.BigDecimal.ONE
import java.math.BigDecimal.ZERO

class CalculatorState(
        var amount: BigDecimal = ZERO,
        var rate: BigDecimal = ZERO,
        var increment: BigDecimal = ONE,
        var incrementUnit: Unit = Unit.PERCENT,
        var decrement: BigDecimal = ONE,
        var decrementUnit: Unit = Unit.PERCENT,
        var currencyFrom: CurrencyCode = CurrencyCode.NONE,
        var currencyTo: CurrencyCode = CurrencyCode.NONE,
        var linkToRate: Boolean = true
) {
    var value: BigDecimal = BigDecimal.ZERO

    data class CalculatorModel(
            val amount: String,
            val value: String,
            val rate: String,
            val increment: String,
            val decrement: String,
            val fromCurrency: String,
            val toCurrency: String,
            @ColorRes val linkColor: Int
    )

    enum class Unit {
        PERCENT, NUMBER;

        fun display() {
            when (this) {
                PERCENT -> "%"
                NUMBER -> ""
            }
        }
    }

    enum class Field {
        NONE, AMOUNT, RATE
    }
}