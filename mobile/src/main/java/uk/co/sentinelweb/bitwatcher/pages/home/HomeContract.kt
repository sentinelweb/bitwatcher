package uk.co.sentinelweb.bitwatcher.pages.home

interface HomeContract {

    interface View {
        fun setData(model: HomeModel)
    }

    interface Presenter {
        fun loadData()
        fun view():View
    }
}