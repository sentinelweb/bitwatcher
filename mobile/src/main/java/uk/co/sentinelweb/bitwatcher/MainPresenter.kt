package uk.co.sentinelweb.bitwatcher

import uk.co.sentinelweb.bitwatcher.pages.PagePresenter

class MainPresenter(val view:MainContract.View) : MainContract.Presenter {
    // TODo fix this
    private val presenters: MutableMap<Int, PagePresenter> = mutableMapOf()


    override fun addPagePresenter(position: Int, presenter: PagePresenter) {
        presenter.init()
        presenters[position]=presenter
    }

    override fun removePagePresenter(position: Int) {
        presenters[position]?.destroy()
        presenters.remove(position)
    }

    override fun onCreate() {

    }

    override fun onStart() {
        for (key in presenters.keys) {
            presenters[key]?.onStart()
        }
    }

    override fun onStop() {
        for (key in presenters.keys) {
            presenters[key]?.onStop()
        }
    }
}