package uk.co.sentinelweb.bitwatcher.activity.pages.home

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.page_home.view.*
import kotlinx.android.synthetic.main.ticker_grid.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.edit_account.EditAccountActivity
import uk.co.sentinelweb.bitwatcher.common.database.entities.FullAccountView

class HomeView( context: Context?): FrameLayout(context), HomeContract.View {


    init {
        LayoutInflater.from(context).inflate(R.layout.page_home, this, true)
    }

    override fun updateTickerState(state: HomeState.TickerState) {
        btc_usd_ticker_text.text = state.btcUsdPriceText
        eth_usd_ticker_text.text = state.ethUsdPriceText
        bch_usd_ticker_text.text = state.bchUsdPriceText
        btc_gbp_ticker_text.text = state.btcGbpPriceText
        eth_gbp_ticker_text.text = state.ethGbpPriceText
        bch_gbp_ticker_text.text = state.bchGbpPriceText
        btc_eur_ticker_text.text = state.btcEurPriceText
        eth_eur_ticker_text.text = state.ethEurPriceText
        bch_eur_ticker_text.text = state.bchEurPriceText
    }

    override fun setAccounts(list: List<FullAccountView>) {
        account.text = list.toString()
        account.setOnClickListener({v -> context.startActivity(EditAccountActivity.getLaunchIntent(context, list.get(0).id))})
    }
}