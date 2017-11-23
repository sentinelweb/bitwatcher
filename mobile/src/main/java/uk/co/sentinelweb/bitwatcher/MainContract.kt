package uk.co.sentinelweb.bitwatcher

import uk.co.sentinelweb.bitwatcher.pages.PagePresenter

interface MainContract {

    interface Presenter {
        fun onCreate()// TODO use lifecycle components
        fun onStart()// TODO use lifecycle components
        fun onStop()// TODO use lifecycle components
        fun addPagePresenter(position:Int, presenter:PagePresenter)
        fun removePagePresenter(position:Int):PagePresenter?
    }

    interface View {

    }
}