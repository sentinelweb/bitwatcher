package uk.co.sentinelweb.bitwatcher.activity.main.pages

import android.arch.lifecycle.LifecycleObserver
import android.view.View

interface PagePresenter : LifecycleObserver{
    fun view(): View
    fun onCreate()
    fun onDestroy()
    fun onEnter()
    fun onExit()
}