package uk.co.sentinelweb.bitwatcher.pages.loops

import uk.co.sentinelweb.bitwatcher.pages.PagePresenter

interface LoopsContract {

    interface View {
        fun setData(model: LoopsModel)
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}