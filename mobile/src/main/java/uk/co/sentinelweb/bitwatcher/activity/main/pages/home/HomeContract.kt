package uk.co.sentinelweb.bitwatcher.activity.main.pages.home

import uk.co.sentinelweb.bitwatcher.activity.main.pages.PagePresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row.AccountRowContract

interface HomeContract {

    interface View {
        fun updateTickerDisplay(tickers: HomeState.TickerDisplay)
        fun addAccount(interactions: AccountRowContract.Interactions): AccountRowContract.Presenter
        fun updateTotalsDisplay(totals: HomeState.TotalsDisplay)
        fun showCurrencyDialog(currencies: Array<String>)
        fun launchEditAccountActivity(id:Long?)
        fun launchAccountOverviewActivity(id:Long)
        fun setDisplayCurrency(currencyCode: String)
        fun setDisplayRealAccounts(display:Boolean)
        fun clearAccountList()
        fun showDeletedSnackBar()
        fun showSnackBarMessage(message:String)
        fun setPresenter(homePresenter: Presenter)
    }

    interface Presenter : PagePresenter{
        fun onCurrencyButtonClick()
        fun onCurrencySelected(code: String)
        fun onAddAccountClick()
        fun onDisplayRealAccountToggle()
        fun restoreDeletedAccount()
        fun clearDeletedAccount()
    }
}