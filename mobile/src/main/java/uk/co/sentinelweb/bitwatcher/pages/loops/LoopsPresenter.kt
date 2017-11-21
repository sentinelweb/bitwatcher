package uk.co.sentinelweb.bitwatcher.pages.loops

import android.view.View


class LoopsPresenter(val view: LoopsContract.View) : LoopsContract.Presenter {

    override fun view(): View {
        return view as View
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun loadData() {

    }
}