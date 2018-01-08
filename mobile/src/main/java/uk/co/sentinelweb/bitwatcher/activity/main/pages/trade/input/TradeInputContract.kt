package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

interface TradeInputContract {

    interface View {
        fun setData(model:TradeInputState.TradeInputDisplayModel)
    }

    interface Presenter {
        fun onCurrencyButtonClick()
        fun onExecuteButtonClick()

    }
}