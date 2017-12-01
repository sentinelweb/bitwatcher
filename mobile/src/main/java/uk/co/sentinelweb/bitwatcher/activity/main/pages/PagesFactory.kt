package uk.co.sentinelweb.bitwatcher.activity.pages

import android.view.ViewGroup
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivity
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivityComponent
import javax.inject.Inject

class PagesFactory @Inject constructor(
        val mainActivityComponent: MainActivityComponent,
        val mainActivity: MainActivity) {


    fun createPagePresenter(container:ViewGroup?, position:Int): PagePresenter {
        when(position) {
            0 -> {
                val homePresenter = mainActivityComponent.homePageBuilder().build().provideHomePresenter()
                container?.addView(homePresenter.view())
                mainActivity.lifecycle.addObserver(homePresenter)
                return homePresenter
            }
            1 -> {
                val loopsPresenter = mainActivityComponent.loopsPageBuilder().build().provideLoopsPresenter()
                container?.addView(loopsPresenter.view())
                mainActivity.lifecycle.addObserver(loopsPresenter)
                return loopsPresenter
            }

            2 -> {
                val tradePresenter = mainActivityComponent.tradePageBuilder().build().provideTradePresenter()
                container?.addView(tradePresenter.view())
                mainActivity.lifecycle.addObserver(tradePresenter)
                return tradePresenter
            }
            else -> {
                throw IllegalArgumentException("Invalid position: ${position}")
            }
        }
    }
}