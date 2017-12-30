package uk.co.sentinelweb.bitwatcher.common.preference

import android.content.SharedPreferences
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import javax.inject.Inject


class CalculatorStateInteractor @Inject constructor(
        private val preferences: SharedPreferences
){

    fun getLastCalculatorState(): CalculatorStatePreferences {
        return CalculatorStatePreferences(
                BigDecimal(preferences.getString(BitwatcherPreferences.CALCULATOR_STATE_AMOUNT, "0")),
                BigDecimal(preferences.getString(BitwatcherPreferences.CALCULATOR_STATE_RATE, "0")),
                BigDecimal(preferences.getString(BitwatcherPreferences.CALCULATOR_STATE_INCREMENT, "0")),
                preferences.getString(BitwatcherPreferences.CALCULATOR_STATE_INCREMENTUNIT, "PERCENT"),
                BigDecimal(preferences.getString(BitwatcherPreferences.CALCULATOR_STATE_DECREMENT, "0")),
                preferences.getString(BitwatcherPreferences.CALCULATOR_STATE_DECREMENTUNIT, "PERCENT"),
                CurrencyCode.lookup(preferences.getString(BitwatcherPreferences.CALCULATOR_STATE_CURRENCYFROM, "NONE")),
                CurrencyCode.lookup(preferences.getString(BitwatcherPreferences.CALCULATOR_STATE_CURRENCYTO, "NONE")),
                preferences.getBoolean(BitwatcherPreferences.CALCULATOR_STATE_LINKTORATE, true)
        )
    }

    fun saveLastCalculatorState(state: CalculatorStatePreferences) {

        preferences.edit()
                .putString(BitwatcherPreferences.CALCULATOR_STATE_AMOUNT, state.amount.dp(6))
                .putString(BitwatcherPreferences.CALCULATOR_STATE_RATE, state.rate.dp(6))
                .putString(BitwatcherPreferences.CALCULATOR_STATE_INCREMENT, state.increment.dp(6))
                .putString(BitwatcherPreferences.CALCULATOR_STATE_INCREMENTUNIT, state.incrementUnit)
                .putString(BitwatcherPreferences.CALCULATOR_STATE_DECREMENT, state.decrement.dp(6))
                .putString(BitwatcherPreferences.CALCULATOR_STATE_DECREMENTUNIT, state.decrementUnit)
                .putString(BitwatcherPreferences.CALCULATOR_STATE_CURRENCYFROM, state.currencyFrom.toString())
                .putString(BitwatcherPreferences.CALCULATOR_STATE_CURRENCYTO, state.currencyTo.toString())
                .putBoolean(BitwatcherPreferences.CALCULATOR_STATE_LINKTORATE, state.linkToRate)
                .apply()
    }

    data class CalculatorStatePreferences constructor(
            var amount: BigDecimal,
            var rate: BigDecimal,
            var increment: BigDecimal,
            var incrementUnit: String,
            var decrement: BigDecimal,
            var decrementUnit: String,
            var currencyFrom: CurrencyCode,
            var currencyTo: CurrencyCode,
            var linkToRate: Boolean
    )

}