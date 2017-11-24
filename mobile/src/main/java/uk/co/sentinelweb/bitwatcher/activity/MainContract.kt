package uk.co.sentinelweb.bitwatcher.activity

import android.arch.lifecycle.LifecycleObserver
import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface MainContract {

    interface Presenter: LifecycleObserver {
        fun addPagePresenter(position:Int, presenter:PagePresenter)
        fun removePagePresenter(position:Int):PagePresenter?
    }

    interface View {
        fun unregisterLifecycleObserver(observer:LifecycleObserver)
    }
}