package uk.co.sentinelweb.bitwatcher.pages

import android.view.View

interface PagePresenter {

    fun view(): View
    fun onStart()// TODO use lifecycle components
    fun onStop()// TODO use lifecycle components
}