package uk.co.sentinelweb.bitwatcher.activity.main.pages.loops

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import javax.inject.Inject


class LoopsPresenter @Inject constructor(private val view: LoopsContract.View) : LoopsContract.Presenter {
    override fun init() {

    }

    override fun cleanup() {

    }

    override fun view(): View {
        return view as View
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {

    }

    override fun loadData() {

    }

    override fun onEnter() {
    }

    override fun onExit() {
    }

}