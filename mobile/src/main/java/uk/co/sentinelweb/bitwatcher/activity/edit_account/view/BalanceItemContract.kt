package uk.co.sentinelweb.bitwatcher.activity.edit_account.view

import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.domain.CurrencyCode

interface BalanceItemContract {
    interface View {
        fun setData(amount: String, currencyCode: String)
        fun setPresenter(p: Presenter)
        fun showError(error: ValidationError)
        fun showCurrencySelector(currencies: Array<String>)
    }

    interface Presenter {
        fun bindData(balanceDomain: BalanceDomain)
        fun onAmountChanged(value: String)
        fun onCurrencyChanged(value: String)
        fun onCurrencyButtonClicked()
        fun onDelete()
        fun init()
        fun setInteractions(interactions: Interactions)
        fun getCurrencyCode(): CurrencyCode?
        fun getBalance():BalanceDomain
    }

    interface Interactions {
        fun validateCurrencyCode(code: CurrencyCode): ValidationError
        fun onDelete(presenter: Presenter)
        fun getCurrencyList(): List<CurrencyCode>
    }
}