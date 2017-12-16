package uk.co.sentinelweb.bitwatcher.activity.edit_account.view

import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class BalanceItemPresenter constructor(
        private val view: BalanceItemContract.View
) : BalanceItemContract.Presenter {

    private var state = BalanceItemState()

    private lateinit var interactions: BalanceItemContract.Interactions

    override fun setInteractions(interactions: BalanceItemContract.Interactions) {
        this.interactions = interactions
    }

    override fun bindData(balanceDomain: BalanceDomain) {
        state.id = balanceDomain.id
        state.amount = balanceDomain.available
        state.code = balanceDomain.currency
        updateView()
    }

    private fun updateView() {
        val amount = state.amount
        view.setData(amount?.dp(2)?:"", state.code.toString())
    }

    override fun init() {
        view.setPresenter(this)
    }

    override fun getBalance(): BalanceDomain {
        return BalanceDomain(state.id, state.code?:CurrencyCode.NONE, state.amount?: ZERO, state.amount?: ZERO, state.amount?: ZERO)
    }

    override fun onCurrencyChanged(value: String) {
        val currencyCode = CurrencyCode.lookup(value)
        if (currencyCode != CurrencyCode.UNKNOWN && currencyCode != CurrencyCode.NONE) {
            val validateCurrencyCode = interactions.validateCurrencyCode(currencyCode)
            if (validateCurrencyCode.code == ValidationError.Type.OK) {
                state.code = currencyCode
                view.showError(ValidationError.OK)
                updateView()
            } else {
                view.showError(validateCurrencyCode)
            }
        } else {
            view.showError(ValidationError("Invalid currency code ; ${value}", ValidationError.Type.VALIDATION))
        }
    }

    override fun onDelete() {
        interactions.onDelete(this)
    }

    override fun onAmountChanged(value: String) {
        try {
            state.amount = BigDecimal(value)
            view.showError(ValidationError.OK)
        } catch (e: Exception) {
            view.showError(ValidationError("Invalid amount", ValidationError.Type.VALIDATION))
        }
    }

    override fun onCurrencyButtonClicked() {
        val currencies = interactions.getCurrencyList()
        val currencyDisplay = arrayOfNulls<String>(currencies.size) // TODO init array nonNull
        currencies.forEachIndexed({i,code -> currencyDisplay[i] =  code.toString()})
        view.showCurrencySelector(currencyDisplay as Array<String>)
    }

    override fun getCurrencyCode(): CurrencyCode? {
        return state.code
    }


}
