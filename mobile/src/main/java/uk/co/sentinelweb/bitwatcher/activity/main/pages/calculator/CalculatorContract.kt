package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import uk.co.sentinelweb.bitwatcher.activity.pages.PagePresenter

interface CalculatorContract {

    interface View {
        fun setData(model: CalculatorState.CalculatorModel, exclude:CalculatorState.Field)
        fun showCurrencyPicker(from:Boolean, currencies:Array<String>)
        fun setPresenter(p:Presenter)
        fun hideKeyBoard()
    }

    interface Presenter : PagePresenter {
        fun onCurrencyFromButtonClick()
        fun onCurrencyToButtonClick()
        fun setCurrencyFrom(currency:String)
        fun setCurrencyTo(currency:String)
        fun onIncrement()
        fun onDecrement()
        fun onRateChanged(value:String)
        fun onAmountChanged(value:String)
        fun toggleLinkRate()
    }
}