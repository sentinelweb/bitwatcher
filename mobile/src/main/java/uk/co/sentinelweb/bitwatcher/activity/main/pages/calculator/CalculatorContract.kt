package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface CalculatorContract {

    interface View {
        fun setData(model: CalculatorState.CalculatorModel)
    }

    interface Presenter : PagePresenter {
        fun loadData()
    }
}