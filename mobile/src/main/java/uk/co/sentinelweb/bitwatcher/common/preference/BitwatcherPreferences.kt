package uk.co.sentinelweb.bitwatcher.common.preference

import android.content.SharedPreferences
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import javax.inject.Inject

class BitwatcherPreferences @Inject constructor(
        val preferences : SharedPreferences) {
    companion object {
        val VIEW_REAL_ITEMS_KEY = "view_real_iteam"
        val SELECTED_CURRENCY_KEY = "selected_currency"

        val CALCULATOR_STATE_AMOUNT = "calculator_state_amount"
        val CALCULATOR_STATE_RATE = "calculator_state_rate"
        val CALCULATOR_STATE_INCREMENT = "calculator_state_increment"
        val CALCULATOR_STATE_INCREMENTUNIT = "calculator_state_increment_unit"
        val CALCULATOR_STATE_DECREMENT = "calculator_state_decrement"
        val CALCULATOR_STATE_DECREMENTUNIT = "calculator_state_decrement_unit"
        val CALCULATOR_STATE_CURRENCYTO = "calculator_state_currency_to"
        val CALCULATOR_STATE_CURRENCYFROM = "calculator_state_currency_from"
        val CALCULATOR_STATE_LINKTORATE = "calculator_state_link_to_rate"
    }
    fun saveViewRealItems(viewRealItems:Boolean) {
        preferences.edit().putBoolean("VIEW_REAL_ITEMS_KEY", viewRealItems).apply()
    }

    fun getViewRealItems():Boolean {
        return preferences.getBoolean("VIEW_REAL_ITEMS_KEY", true)
    }

    fun saveSelectedCurrency(code:CurrencyCode) {
        preferences.edit().putString("SELECTED_CURRENCY_KEY", code.toString()).apply()
    }
    fun getSelectedCurrency():CurrencyCode? {
        return CurrencyCode.lookup(preferences.getString("SELECTED_CURRENCY_KEY","GBP"))
    }

    fun getLastCalculatorState(): CalculatorStatePreferences {
        return CalculatorStatePreferences(
                BigDecimal(preferences.getString(CALCULATOR_STATE_AMOUNT,"0")),
                BigDecimal(preferences.getString(CALCULATOR_STATE_RATE,"0")),
                BigDecimal(preferences.getString(CALCULATOR_STATE_INCREMENT,"0")),
                preferences.getString(CALCULATOR_STATE_INCREMENTUNIT,"PERCENT"),
                BigDecimal(preferences.getString(CALCULATOR_STATE_DECREMENT,"0")),
                preferences.getString(CALCULATOR_STATE_DECREMENTUNIT,"PERCENT"),
                CurrencyCode.lookup(preferences.getString(CALCULATOR_STATE_CURRENCYFROM,"NONE"))!!,
                CurrencyCode.lookup(preferences.getString(CALCULATOR_STATE_CURRENCYTO,"NONE"))!!,
                preferences.getBoolean(CALCULATOR_STATE_LINKTORATE,true)
        )
    }

    fun saveLastCalculatorState(state: CalculatorStatePreferences) {

        preferences.edit()
                .putString(CALCULATOR_STATE_AMOUNT, state.amount.dp(6))
                .putString(CALCULATOR_STATE_RATE, state.rate.dp(6))
                .putString(CALCULATOR_STATE_INCREMENT, state.increment.dp(6))
                .putString(CALCULATOR_STATE_INCREMENTUNIT, state.incrementUnit)
                .putString(CALCULATOR_STATE_DECREMENT, state.decrement.dp(6))
                .putString(CALCULATOR_STATE_DECREMENTUNIT, state.decrementUnit)
                .putString(CALCULATOR_STATE_CURRENCYFROM, state.currencyFrom.toString())
                .putString(CALCULATOR_STATE_CURRENCYTO, state.currencyTo.toString())
                .putBoolean(CALCULATOR_STATE_LINKTORATE, state.linkToRate)
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