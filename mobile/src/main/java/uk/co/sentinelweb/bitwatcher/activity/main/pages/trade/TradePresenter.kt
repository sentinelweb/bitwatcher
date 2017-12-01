package uk.co.sentinelweb.bitwatcher.activity.pages.trade

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import javax.inject.Inject


class TradePresenter @Inject constructor(val view: TradeContract.View) : TradeContract.Presenter {
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
}