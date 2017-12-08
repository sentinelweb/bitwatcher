package uk.co.sentinelweb.bitwatcher.activity.main.pages

import android.view.ViewGroup
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivity
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivityComponent
import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter
import javax.inject.Inject

class PagesFactory @Inject constructor(
        private val mainActivityComponent: MainActivityComponent,
        private val mainActivity: MainActivity) {


    fun createPagePresenter(container:ViewGroup?, position:Int): PagePresenter {
        when(position) {
            0 -> {
                val homePresenter = mainActivityComponent.homePageBuilder().build().provideHomePresenter()
                container?.addView(homePresenter.view())
                return homePresenter
            }
            1 -> {
                val calcPresenter = mainActivityComponent.calcPageBuilder().build().provideCalcPresenter()
                container?.addView(calcPresenter.view())
                return calcPresenter
            }

            2 -> {
                val loopsPresenter = mainActivityComponent.loopsPageBuilder().build().provideLoopsPresenter()
                container?.addView(loopsPresenter.view())
                return loopsPresenter
            }

            3 -> {
                val tradePresenter = mainActivityComponent.tradePageBuilder().build().provideTradePresenter()
                container?.addView(tradePresenter.view())
                return tradePresenter
            }
            else -> {
                throw IllegalArgumentException("Invalid position: ${position}")
            }
        }
    }
}