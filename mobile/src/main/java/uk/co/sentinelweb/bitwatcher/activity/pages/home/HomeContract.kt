package uk.co.sentinelweb.bitwatcher.activity.pages.home

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter
import uk.co.sentinelweb.bitwatcher.database.entities.FullAccount

interface HomeContract {

    interface View {
        fun updateTickerState(state: HomeState.TickerState)
        fun setAccounts(list: List<FullAccount>)
    }

    interface Presenter : PagePresenter{

    }
}