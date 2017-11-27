package uk.co.sentinelweb.bitwatcher.activity.pages.home

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.page_home.view.*
import kotlinx.android.synthetic.main.ticker_grid.view.*
import uk.co.sentinelweb.bitwatcher.R

class HomeView( context: Context?): FrameLayout(context), HomeContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.page_home, this, true)
    }

    override fun setData(model: HomeModel) {
        btc_usd_ticker_text.text = model.btcUsdPriceText
        eth_usd_ticker_text.text = model.ethUsdPriceText
        bch_usd_ticker_text.text = model.bchUsdPriceText
        btc_gbp_ticker_text.text = model.btcGbpPriceText
        eth_gbp_ticker_text.text = model.ethGbpPriceText
        bch_gbp_ticker_text.text = model.bchGbpPriceText
        btc_eur_ticker_text.text = model.btcEurPriceText
        eth_eur_ticker_text.text = model.ethEurPriceText
        bch_eur_ticker_text.text = model.bchEurPriceText
    }

}