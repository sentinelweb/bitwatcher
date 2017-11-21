package uk.co.sentinelweb.bitwatcher.pages

import android.view.ViewGroup
import uk.co.sentinelweb.bitwatcher.pages.home.HomePresenter
import uk.co.sentinelweb.bitwatcher.pages.home.HomeView
import uk.co.sentinelweb.bitwatcher.pages.loops.LoopsPresenter
import uk.co.sentinelweb.bitwatcher.pages.loops.LoopsView
import uk.co.sentinelweb.bitwatcher.pages.trade.TradePresenter
import uk.co.sentinelweb.bitwatcher.pages.trade.TradeView

class PagerFactory() {

    fun createPagePresenter(container:ViewGroup?, position:Int):PagePresenter {
        when(position) {
            0 -> {
                val homeView = HomeView(container?.context)
                container?.addView(homeView)
                val homePresenter = HomePresenter(homeView)
                homePresenter.loadData() // TODO intinalise properly
                return homePresenter
            }
            1 -> {
                val loopsView = LoopsView(container?.context)
                container?.addView(loopsView)
                return LoopsPresenter(loopsView)
            }

            2 -> {
                val tradeView = TradeView(container?.context)
                container?.addView(tradeView)
                return TradePresenter(tradeView)
            }
            else -> {
                throw IllegalArgumentException("Invalid position: ${position}");
            }
        }
    }
}