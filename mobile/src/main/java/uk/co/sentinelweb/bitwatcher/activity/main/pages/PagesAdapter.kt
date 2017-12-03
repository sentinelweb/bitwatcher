package uk.co.sentinelweb.bitwatcher.activity.main.pages

import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup
import uk.co.sentinelweb.bitwatcher.activity.main.MainContract
import javax.inject.Inject

class PagesAdapter @Inject constructor(
        private val presenter: MainContract.Presenter,
        private val pagesFactory: PagesFactory
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
        val pagePresenter = pagesFactory.createPagePresenter(container, position)
        presenter.addPagePresenter(position, pagePresenter)
        Log.d("MainActivity", "add item ${position}")
        return pagePresenter.view()
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        Log.d("MainActivity", "remove item ${position}")
        val removePagePresenter = presenter.removePagePresenter(position)
        container?.removeView(removePagePresenter?.view())
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return 3
    }
}