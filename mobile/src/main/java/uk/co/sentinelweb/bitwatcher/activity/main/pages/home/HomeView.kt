package uk.co.sentinelweb.bitwatcher.activity.main.pages.home

import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.main_home_accounts_include.view.*
import kotlinx.android.synthetic.main.main_home_page.view.*
import kotlinx.android.synthetic.main.main_home_ticker_grid_include.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.edit_account.EditAccountActivity
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row.AccountRowContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row.AccountRowPresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row.AccountRowView
import uk.co.sentinelweb.bitwatcher.common.ui.CurrencySelector


class HomeView(context: Context?) : FrameLayout(context), HomeContract.View {

    private var fabHideListener: FabHideListener
    private lateinit var presenter: HomeContract.Presenter
    private var snackBar: Snackbar? = null


    init {
        LayoutInflater.from(context).inflate(R.layout.main_home_page, this, true)
        fabHideListener = FabHideListener(home_accounts_scroll, home_accounts_add_fab)
        viewTreeObserver.addOnGlobalLayoutListener(
                { home_accounts_scroll.layoutParams.height = height - home_ticker_include.height - home_accounts_include.top })
        home_accounts_scroll.getViewTreeObserver().addOnScrollChangedListener(fabHideListener)
        home_accounts_ghost_container.setOnClickListener({
            fabHideListener.toggleFab()
        })
        home_accounts_add_fab.setOnClickListener({ _ -> presenter.onAddAccountClick() })
    }

    override fun updateTickerDisplay(tickers: HomeState.TickerDisplay) {
        btc_usd_ticker_text.text = tickers.btcUsdPriceText
        eth_usd_ticker_text.text = tickers.ethUsdPriceText
        bch_usd_ticker_text.text = tickers.bchUsdPriceText
        btc_gbp_ticker_text.text = tickers.btcGbpPriceText
        eth_gbp_ticker_text.text = tickers.ethGbpPriceText
        bch_gbp_ticker_text.text = tickers.bchGbpPriceText
        btc_eur_ticker_text.text = tickers.btcEurPriceText
        eth_eur_ticker_text.text = tickers.ethEurPriceText
        bch_eur_ticker_text.text = tickers.bchEurPriceText
        xrp_usd_ticker_text.text = tickers.xrpUsdPriceText
        xrp_gbp_ticker_text.text = tickers.xrpGbpPriceText
        xrp_eur_ticker_text.text = tickers.xrpEurPriceText
        iota_usd_ticker_text.text = tickers.iotaUsdPriceText
        iota_gbp_ticker_text.text = tickers.iotaGbpPriceText
        iota_eur_ticker_text.text = tickers.iotaEurPriceText
    }

    override fun setPresenter(p: HomeContract.Presenter) {
        presenter = p
        home_accounts_currency_button.setOnClickListener({ _ -> presenter.onCurrencyButtonClick() })
        home_accounts_real_visible_button.setOnClickListener({ _ -> presenter.onDisplayRealAccountToggle() })
    }

    override fun updateTotalsDisplay(totals: HomeState.TotalsDisplay) {
        home_accounts_ghost_total.text = totals.ghostTotalDisplay
        home_accounts_real_total.text = totals.realTotalDisplay
    }

    override fun addAccount(interactions: AccountRowContract.Interactions): AccountRowContract.Presenter {
        val view = AccountRowView(context)
        home_accounts_container.addView(view)
        view.setOnClickListener(null) // TODO link to acct overview page
        return AccountRowPresenter(view, interactions)
    }

    override fun clearAccountList() {
        home_accounts_container.removeAllViews()
    }

    override fun setDisplayCurrency(currencyCode: String) {
        home_accounts_currency_button.text = currencyCode
    }

    override fun showCurrencyDialog(currencies: Array<String>) {
        CurrencySelector.showCurrencySelector(
                context,
                currencies,
                { _: DialogInterface, idx: Int -> presenter.onCurrencySelected(currencies.get(idx)) })
    }

    override fun launchEditAccountActivity(id: Long?) {
        context.startActivity(EditAccountActivity.getLaunchIntent(context, id))
    }

    override fun launchAccountOverviewActivity(id: Long) {
    }

    override fun setDisplayRealAccounts(display: Boolean) {
        home_accounts_real_visible_button.setImageResource(
                if (display) R.drawable.ic_visibility_black_24dp
                else R.drawable.ic_visibility_off_black_24dp
        )
        home_accounts_real_container.visibility = if (display) View.VISIBLE else View.GONE
        requestLayout() //fab button jumps up
    }

    override fun showDeletedSnackBar() {
        snackBar?.dismiss()
        snackBar = Snackbar.make(this, "Account deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", { presenter.restoreDeletedAccount() })
                .setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        presenter.clearDeletedAccount()
                    }
                })
        snackBar?.show()
    }

    override fun showSnackBarMessage(message: String) {
        snackBar?.dismiss()
        snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        snackBar?.show()
    }


}
