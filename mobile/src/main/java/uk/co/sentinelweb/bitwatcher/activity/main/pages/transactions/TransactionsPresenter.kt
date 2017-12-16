package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class TransactionsPresenter @Inject constructor(
        private val view: TransactionsContract.View


) : TransactionsContract.Presenter {

    val subscriptions = CompositeDisposable()
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