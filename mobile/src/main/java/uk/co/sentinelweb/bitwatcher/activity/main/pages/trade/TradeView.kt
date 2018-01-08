package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.main_trade_page.view.*
import uk.co.sentinelweb.bitwatcher.R


class TradeView(context: Context?): FrameLayout(context), TradeContract.View {
    lateinit var presenter:TradeContract.Presenter
    init {
        LayoutInflater.from(context).inflate(R.layout.main_trade_page, this, true)
        trade_account_button.setOnClickListener({presenter.onAccountButtonClick()})
        trade_market_button.setOnClickListener({presenter.onMarketButtonClick()})

    }

    override fun setData(model: TradeState.TradeDisplayModel) {
        trade_account_button.text = model.accountButtonLabel
        trade_account_info.text = model.accountInfo
        trade_market_button.text = model.accountButtonLabel
        trade_market_info.text = model.marketInfo
    }
}