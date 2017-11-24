package uk.co.sentinelweb.bitwatcher.activity.pages.trade

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R

class TradeView(context: Context?): FrameLayout(context), TradeContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.page_trade, this, true)
    }

    override fun setData(model: TradeModel) {

    }
}