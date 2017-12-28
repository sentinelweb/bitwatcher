package uk.co.sentinelweb.bitwatcher.common.ui

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import uk.co.sentinelweb.bitwatcher.R


class CurrencySelector {
    companion object {
        fun showCurrencySelector(context:Context, currencies: Array<String>, function: (DialogInterface, Int) -> Unit) {
            val items = arrayOfNulls<CharSequence>(currencies.size)
            currencies.forEachIndexed { index, type -> items[index] = type }
            AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.title_select_currency))
                    .setItems(items, function)
                    .setCancelable(true)
                    .create()
                    .show()
        }
    }
}