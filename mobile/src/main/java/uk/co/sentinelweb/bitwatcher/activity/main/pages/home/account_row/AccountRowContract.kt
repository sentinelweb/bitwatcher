package uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TickerDomain

interface AccountRowContract {
    interface Presenter {
        fun init()
        fun bindData(account:AccountDomain, code: CurrencyCode, prices: Map<String,TickerDomain>)
        fun onOverflowClick()
        fun onEditClick()
        fun onDeleteClick()
        fun onCheckClick(value:Boolean)
        fun onClick()
        fun setVisible(visible:Boolean)

    }

    interface View {
        fun updateView(data:AccountRowState.DisplayData)
        fun showOverflow()
        fun setPresenter(accountRowPresenter: AccountRowPresenter)
        fun setVisible(visible: Boolean)
    }

    interface Interactions {
        fun onChecked(account:AccountDomain, checked:Boolean)
        fun onEdit(account:AccountDomain)
        fun onDelete(account:AccountDomain)
        fun onClick(account:AccountDomain)
    }
}