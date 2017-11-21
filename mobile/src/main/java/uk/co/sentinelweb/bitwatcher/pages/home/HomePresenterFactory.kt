package uk.co.sentinelweb.bitwatcher.pages.home

import android.content.Context
import android.view.ViewGroup

class HomePresenterFactory {

    fun createHomePresenter(c: Context, container: ViewGroup?) : HomeContract.Presenter {
        val homeView = HomeView(c)
        container?.addView(homeView)
        return HomePresenter(homeView)
    }
}