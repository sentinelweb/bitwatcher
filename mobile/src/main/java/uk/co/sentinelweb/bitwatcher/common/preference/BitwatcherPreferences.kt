package uk.co.sentinelweb.bitwatcher.common.preference

import android.content.SharedPreferences
import uk.co.sentinelweb.domain.CurrencyCode
import javax.inject.Inject

class BitwatcherPreferences @Inject constructor(
        private val preferences: SharedPreferences
) {
    companion object {
        val VIEW_REAL_ITEMS_KEY = "view_real_iteam"
        val SELECTED_CURRENCY_KEY = "selected_currency"
    }

    fun saveViewRealItems(viewRealItems: Boolean) {
        preferences.edit().putBoolean(VIEW_REAL_ITEMS_KEY, viewRealItems).apply()
    }

    fun getViewRealItems(): Boolean {
        return preferences.getBoolean(VIEW_REAL_ITEMS_KEY, true)
    }

    fun saveSelectedCurrency(code: CurrencyCode) {
        preferences.edit().putString(SELECTED_CURRENCY_KEY, code.toString()).apply()
    }

    fun getSelectedCurrency(): CurrencyCode? {
        return CurrencyCode.lookup(preferences.getString(SELECTED_CURRENCY_KEY, "GBP"))
    }

}