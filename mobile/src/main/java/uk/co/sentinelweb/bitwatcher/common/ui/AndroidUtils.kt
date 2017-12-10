package uk.co.sentinelweb.bitwatcher.common.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class AndroidUtils {
    companion object {
        fun hideSoftKeyboard(editText: EditText) {
            val inputMethodManager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0)
        }
    }
}