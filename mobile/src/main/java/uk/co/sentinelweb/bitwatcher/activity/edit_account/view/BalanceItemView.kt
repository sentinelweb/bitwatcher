package uk.co.sentinelweb.bitwatcher.activity.edit_account.view

import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.edit_account_position_view.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.ui.CurrencySelector
import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError

class BalanceItemView(context: Context?) : RelativeLayout(context), BalanceItemContract.View {
    private lateinit var presenter: BalanceItemContract.Presenter

    init {
        LayoutInflater.from(context).inflate(R.layout.edit_account_position_view, this, true)
    }

    override fun setPresenter(p: BalanceItemContract.Presenter) {
        this.presenter = p
        balance_edit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onAmountChanged(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        currency_edit_button.setOnClickListener { _ -> presenter.onCurrencyButtonClicked() }
        balance_delete_button.setOnClickListener({ presenter.onDelete() })
    }

    override fun setData(amount: String, currencyCode: String) {
        currency_edit_button.text = currencyCode
        balance_edit.setText(amount)
    }

    override fun showError(error: ValidationError) {
        balance_error_text.text = error.message
    }

    override fun showCurrencySelector(currencies: Array<String>) {
        CurrencySelector.showCurrencySelector(
                context,
                currencies,
                { _: DialogInterface, idx: Int -> presenter.onCurrencyChanged(currencies.get(idx)) })
    }




}
