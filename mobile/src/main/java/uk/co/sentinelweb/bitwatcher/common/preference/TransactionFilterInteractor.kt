package uk.co.sentinelweb.bitwatcher.common.preference

import android.content.SharedPreferences
import com.google.gson.Gson
import uk.co.sentinelweb.domain.TransactionFilterDomain
import java.util.*
import javax.inject.Inject


class TransactionFilterInteractor @Inject constructor(
        private val preferences: SharedPreferences,
        private val gson: Gson
){
    fun getTransactionFilter(name: String): TransactionFilterDomain? {
        val json = preferences.getString(BitwatcherPreferences.TRANSACTION_FILTER_PREFIX + name, null)
        if (json != null) {
            return gson.fromJson(json, TransactionFilterDomain::class.java)
        } else {
            return null
        }
    }

    fun saveTransactionFilter(name: String, filter: TransactionFilterDomain): TransactionFilterDomain {
        val updatedFilter = filter.copy(name=name, saveDate = Date())
        val namesSet = preferences.getStringSet(BitwatcherPreferences.TRANSACTION_FILTER_NAMES, null)?.toMutableSet()?: mutableSetOf()
        namesSet.add(name)
        preferences.edit()
                .putString(BitwatcherPreferences.TRANSACTION_FILTER_PREFIX + name, gson.toJson(updatedFilter))
                .putStringSet(BitwatcherPreferences.TRANSACTION_FILTER_NAMES, namesSet)
                .apply()
        return updatedFilter
    }

    fun listTransactionFilterNames():List<String> {
        val namesList = preferences.getStringSet(BitwatcherPreferences.TRANSACTION_FILTER_NAMES, null)?.toMutableList()?: mutableListOf()
        Collections.sort(namesList)
        return namesList
    }

    fun deleteTransactionFilter(name: String) {
        val namesSet = preferences.getStringSet(BitwatcherPreferences.TRANSACTION_FILTER_NAMES, null)?.toMutableSet()?: mutableSetOf()
        namesSet.remove(name)
        preferences.edit()
                .remove(BitwatcherPreferences.TRANSACTION_FILTER_PREFIX + name)
                .putStringSet(BitwatcherPreferences.TRANSACTION_FILTER_NAMES, namesSet)
                .apply()
    }
}