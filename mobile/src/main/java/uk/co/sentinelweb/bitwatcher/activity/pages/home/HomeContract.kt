package uk.co.sentinelweb.bitwatcher.activity.pages.home

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface HomeContract {

    interface View {
        fun updateTickerState(state: HomeState.TickerState)
    }

    interface Presenter : PagePresenter{

    }
}