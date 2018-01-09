package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.main_trade_page.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input.TradeInputPresenterFactory


class TradeView(context: Context?) : FrameLayout(context), TradeContract.View {
    lateinit var presenter: TradeContract.Presenter

    init {
        LayoutInflater.from(context).inflate(R.layout.main_trade_page, this, true)
        trade_account_button.setOnClickListener({ presenter.onAccountButtonClick() })
        trade_market_button.setOnClickListener({ presenter.onMarketButtonClick() })
        trade_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                presenter.onTabClicked(tab.position == 0)
            }
        })
    }

    override fun setViewPresenter(p: TradeContract.Presenter) {
        presenter = p
    }

    override fun getInputPresenter(
            inputPresenterFactory: TradeInputPresenterFactory,
            isBuy: Boolean
    ): TradeInputContract.Presenter {
        val view = if (isBuy) trade_buy_view else trade_sell_view
        return inputPresenterFactory.createPresenter(view)
    }

    override fun showTabContent(isBuy: Boolean) {
        trade_buy_view.visibility = if (isBuy) View.VISIBLE else View.GONE
        trade_sell_view.visibility = if (!isBuy) View.VISIBLE else View.GONE
    }

    override fun setData(model: TradeState.TradeDisplayModel) {
        trade_account_button.text = model.accountButtonLabel
        trade_account_info.text = model.accountInfo
        trade_market_button.text = model.accountButtonLabel
        trade_market_info.text = model.marketInfo
    }

    override fun showAccountSeletor(accounts: Array<String>) {
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_select_currency))
                .setItems(accounts, { _, index -> presenter.onAccountSelected(index) })
                .setCancelable(true)
                .create()
                .show()
    }

    override fun showMarketsSelector(marketNames: Array<String>) {
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_select_currency))
                .setItems(marketNames, { _, index -> presenter.onMarketSelected(index) })
                .setCancelable(true)
                .create()
                .show()
    }
}
