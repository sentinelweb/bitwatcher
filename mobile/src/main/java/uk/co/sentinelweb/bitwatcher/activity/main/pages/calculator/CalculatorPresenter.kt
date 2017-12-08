package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import javax.inject.Inject


class CalculatorPresenter @Inject constructor(private val view: CalculatorContract.View) : CalculatorContract.Presenter {

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