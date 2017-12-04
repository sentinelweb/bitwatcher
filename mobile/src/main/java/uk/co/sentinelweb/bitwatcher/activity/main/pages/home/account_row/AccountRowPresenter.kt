package uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row

import uk.co.sentinelweb.bitwatcher.domain.AccountDomain
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain

class AccountRowPresenter constructor(
        private val view:AccountRowContract.View,
        private val interactions:AccountRowContract.Interactions,
        private val displayMapper: AccountDisplayDataMapper = AccountDisplayDataMapper(),
        private val state: AccountRowState = AccountRowState(AccountDomain.NONE, false, CurrencyCode.NONE)
) : AccountRowContract.Presenter {
    override fun init() {
        view.setPresenter(this)
    }

    override fun bindData(account: AccountDomain, code: CurrencyCode, prices: Map<String, TickerDomain>) {
        state.domain = account
        state.totalCurrency = code
        state.displayData = displayMapper.map(account, code, prices)
        view.updateView(state.displayData!!)
        view.setVisible(state.visible)
    }

    override fun setVisible(visible: Boolean) {
        state.visible = visible
        view.setVisible(state.visible)
    }

    override fun onOverflowClick() {
        view.showOverflow()
    }

    override fun onEditClick() {
        interactions.onEdit(state.domain)
    }

    override fun onDeleteClick() {
        interactions.onDelete(state.domain)
    }

    override fun onCheckClick(value: Boolean) {
        state.checked = value
        interactions.onChecked(state.domain, value)
    }

    override fun onClick() {
        interactions.onClick(state.domain)
    }

}