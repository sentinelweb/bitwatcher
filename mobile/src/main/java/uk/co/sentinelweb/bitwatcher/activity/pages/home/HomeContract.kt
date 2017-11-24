package uk.co.sentinelweb.bitwatcher.activity.pages.home

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface HomeContract {

    interface View {
        fun setData(model: HomeModel)
    }

    interface Presenter : PagePresenter{
        fun loadData()
    }
}