package uk.co.sentinelweb.bitwatcher.common.extensions

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun Fragment.putExtras(a: AppCompatActivity)  {
    this.arguments = Bundle()
    this.arguments.putAll(a.intent.extras)
}

fun Fragment.add(a: AppCompatActivity, @IdRes id:Int) {
    a.supportFragmentManager
            .beginTransaction()
            .add(id, this)
            .commitNow()
}

fun Fragment.addWithExtras(a: AppCompatActivity, id:Int) {
    this.putExtras(a)
    this.add(a, id)
}