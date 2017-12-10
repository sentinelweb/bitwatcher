package uk.co.sentinelweb.bitwatcher.activity.pages

import android.arch.lifecycle.LifecycleObserver
import android.view.View

interface PagePresenter : LifecycleObserver{

    fun view(): View
    fun init()
    fun cleanup()
    fun onEnter()
    fun onExit()
}