package uk.co.sentinelweb.bitwatcher.common.mapper

import android.content.Context
import android.support.annotation.StringRes
import javax.inject.Inject

class StringMapper @Inject constructor(
        private val context:Context
) {
    fun getString(@StringRes id:Int):String {
        return context.getString(id)
    }
}