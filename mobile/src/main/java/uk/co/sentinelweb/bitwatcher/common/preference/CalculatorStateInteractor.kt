package uk.co.sentinelweb.bitwatcher.common.preference

import android.content.SharedPreferences
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import javax.inject.Inject


class CalculatorStateInteractor @Inject constructor(
        private val preferences: SharedPreferences
){
    companion object {
        val CALCULATOR_STATE_AMOUNT = "calculator_state_amount"
        val CALCULATOR_STATE_RATE = "calculator_state_rate"
        val CALCULATOR_STATE_CURRENCYTO = "calculator_state_currency_to"
        val CALCULATOR_STATE_CURRENCYFROM = "calculator_state_currency_from"
        val CALCULATOR_STATE_LINKTORATE = "calculator_state_link_to_rate"
    }
    fun getLastCalculatorState(): CalculatorStatePreferences {
        return CalculatorStatePreferences(
                BigDecimal(preferences.getString(CALCULATOR_STATE_AMOUNT, "0")),
                BigDecimal(preferences.getString(CALCULATOR_STATE_RATE, "0")),
                CurrencyCode.lookup(preferences.getString(CALCULATOR_STATE_CURRENCYFROM, "NONE")),
                CurrencyCode.lookup(preferences.getString(CALCULATOR_STATE_CURRENCYTO, "NONE")),
                preferences.getBoolean(CALCULATOR_STATE_LINKTORATE, true)
        )
    }

    fun saveLastCalculatorState(state: CalculatorStatePreferences) {

        preferences.edit()
                .putString(CALCULATOR_STATE_AMOUNT, state.amount.dp(6))
                .putString(CALCULATOR_STATE_RATE, state.rate.dp(6))
                .putString(CALCULATOR_STATE_CURRENCYFROM, state.currencyFrom.toString())
                .putString(CALCULATOR_STATE_CURRENCYTO, state.currencyTo.toString())
                .putBoolean(CALCULATOR_STATE_LINKTORATE, state.linkToRate)
                .apply()
    }

    data class CalculatorStatePreferences constructor(
            var amount: BigDecimal,
            var rate: BigDecimal,
            var currencyFrom: CurrencyCode,
            var currencyTo: CurrencyCode,
            var linkToRate: Boolean
    )

}