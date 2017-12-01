package uk.co.sentinelweb.bitwatcher.activity.pages.home

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter
import uk.co.sentinelweb.bitwatcher.common.database.entities.FullAccountView

interface HomeContract {

    interface View {
        fun updateTickerState(state: HomeState.TickerState)
        fun setAccounts(list: List<FullAccountView>)
    }

    interface Presenter : PagePresenter{

    }
}