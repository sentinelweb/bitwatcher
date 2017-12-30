package uk.co.sentinelweb.bitwatcher.common.preference

import android.content.SharedPreferences
import com.google.gson.Gson
import uk.co.sentinelweb.domain.CurrencyCode
import javax.inject.Inject

class BitwatcherPreferences @Inject constructor(
        private val preferences: SharedPreferences,
        private val gson: Gson
) {
    companion object {
        val VIEW_REAL_ITEMS_KEY = "view_real_iteam"
        val SELECTED_CURRENCY_KEY = "selected_currency"
        val TRANSACTION_FILTER_PREFIX = "transaction_filter."
        val TRANSACTION_FILTER_NAMES = "transaction_filter_names"

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

    fun saveViewRealItems(viewRealItems: Boolean) {
        preferences.edit().putBoolean("VIEW_REAL_ITEMS_KEY", viewRealItems).apply()
    }

    fun getViewRealItems(): Boolean {
        return preferences.getBoolean("VIEW_REAL_ITEMS_KEY", true)
    }

    fun saveSelectedCurrency(code: CurrencyCode) {
        preferences.edit().putString("SELECTED_CURRENCY_KEY", code.toString()).apply()
    }

    fun getSelectedCurrency(): CurrencyCode? {
        return CurrencyCode.lookup(preferences.getString("SELECTED_CURRENCY_KEY", "GBP"))
    }

}