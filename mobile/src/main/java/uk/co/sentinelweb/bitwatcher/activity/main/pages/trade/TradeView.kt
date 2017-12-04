package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.pages.trade.TradeContract
import uk.co.sentinelweb.bitwatcher.activity.pages.trade.TradeModel

class TradeView(context: Context?): FrameLayout(context), TradeContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.main_trade_page, this, true)
    }

    override fun setData(model: TradeModel) {

    }
}