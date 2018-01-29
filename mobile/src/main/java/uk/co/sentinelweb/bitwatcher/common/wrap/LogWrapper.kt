package uk.co.sentinelweb.bitwatcher.common.wrap

import android.util.Log
import javax.inject.Inject

class LogWrapper @Inject constructor(
) {
    private lateinit  var tag:String

    fun tag(obj:Any) {
        tag = obj::class.java.simpleName
    }

    fun e(msg: String) {
        Log.e(tag, msg)
    }

    fun e(msg: String, e: Throwable) {
        Log.e(tag, msg, e)
    }

    fun w(msg: String) {
        Log.w(tag, msg)
    }

    fun w(msg: String, e: Throwable) {
        Log.w(tag, msg, e)
    }

    fun i(msg: String) {
        Log.i(tag, msg)
    }

    fun i(msg: String, e: Throwable) {
        Log.i(tag, msg, e)
    }

    fun d(msg: String) {
        Log.d(tag, msg)
    }

    fun d(msg: String, e: Throwable) {
        Log.d(tag, msg, e)
    }

}