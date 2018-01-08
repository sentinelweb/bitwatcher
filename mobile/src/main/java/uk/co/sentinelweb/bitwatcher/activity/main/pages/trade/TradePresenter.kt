package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import javax.inject.Inject


class TradePresenter @Inject constructor(val view: TradeContract.View) : TradeContract.Presenter {
    override fun onEnter() {
    }

    override fun onExit() {
    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun view(): View {
        return view as View
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {

    }

    override fun onAccountButtonClick() {
    }

    override fun onMarketButtonClick() {
    }


}