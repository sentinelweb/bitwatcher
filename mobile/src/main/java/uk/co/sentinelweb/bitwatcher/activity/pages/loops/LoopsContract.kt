package uk.co.sentinelweb.bitwatcher.activity.pages.loops

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface LoopsContract {

    interface View {
        fun setData(model: LoopsModel)
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}