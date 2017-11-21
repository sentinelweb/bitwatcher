package uk.co.sentinelweb.bitwatcher

import android.util.SparseArray
import uk.co.sentinelweb.bitwatcher.pages.PagePresenter

class MainPresenter(val view:MainContract.View):MainContract.Presenter {
    private val presenters: SparseArray<PagePresenter> = SparseArray()

    override fun addPagePresenter(position: Int, presenter: PagePresenter) {
        presenters.setValueAt(position, presenter)
    }

    override fun removePagePresenter(position: Int) {
        presenters.removeAt(position)
    }

    override fun onCreate() {

    }

    override fun onStart() {
        for (i in 0 .. presenters.size()) {
            presenters[presenters.keyAt(i)].onStart()
        }
    }

    override fun onStop() {
        for (i in 0 .. presenters.size()) {
            presenters[presenters.keyAt(i)].onStop()
        }
    }
}