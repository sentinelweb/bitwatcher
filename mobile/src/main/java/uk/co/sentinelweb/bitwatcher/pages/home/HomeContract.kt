package uk.co.sentinelweb.bitwatcher.pages.home

import uk.co.sentinelweb.bitwatcher.pages.PagePresenter

interface HomeContract {

    interface View {
        fun setData(model: HomeModel)
    }

    interface Presenter : PagePresenter{
        fun loadData()
    }
}