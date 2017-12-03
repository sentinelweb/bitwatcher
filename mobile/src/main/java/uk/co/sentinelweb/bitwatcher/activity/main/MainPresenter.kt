package uk.co.sentinelweb.bitwatcher.activity.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val view: MainContract.View
) : MainContract.Presenter {

    private val presenters: MutableMap<Int, PagePresenter> = mutableMapOf()

    override fun addPagePresenter(position: Int, presenter: PagePresenter) {
        presenter.init()
        presenters[position] = presenter
    }

    override fun removePagePresenter(position: Int): PagePresenter? {
        val pagePresenter = presenters[position]
        if (pagePresenter != null) {
            pagePresenter.cleanup()
            view.unregisterLifecycleObserver(pagePresenter)
        }
        presenters.remove(position)
        return pagePresenter
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {

    }
}