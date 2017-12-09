package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import android.content.Context
import android.content.DialogInterface
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.main_calc_page.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.ui.CurrencySelector

class CalculatorView(context: Context?) : FrameLayout(context), CalculatorContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.main_calc_page, this, true)
        calc_amount_edit_value.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (!viewIsUpdating) {
                    presenter.onAmountChanged(p0.toString())
                }
            }

            override fun afterTextChanged(p0: Editable) {}

            override fun beforeTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        calc_rate_edit_value.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!viewIsUpdating) {
                    presenter.onRateChanged(p0.toString())
                }
            }

            override fun afterTextChanged(p0: Editable) {}

            override fun beforeTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        calc_amount_currency_button.setOnClickListener { _ -> presenter.onCurrencyFromButtonClick() }
        calc_to_currency_button.setOnClickListener { _ -> presenter.onCurrencyToButtonClick() }
        calc_to_link_button.setOnClickListener { _ -> presenter.toggleLinkRate() }
    }

    private lateinit var presenter: CalculatorContract.Presenter
    private var viewIsUpdating = false


    override fun setData(model: CalculatorState.CalculatorModel, exclude: CalculatorState.Field) {
        viewIsUpdating = true
        if (CalculatorState.Field.AMOUNT != exclude) {
            calc_amount_edit_value.setText(model.amount)
        }
        if (CalculatorState.Field.RATE != exclude) {
            calc_rate_edit_value.setText(model.rate)
        }

        calc_rate_increment_button.text = model.increment
        calc_rate_decrement_button.text = model.decrement
        calc_to_currency_button.setText(model.toCurrency)
        calc_amount_currency_button.setText(model.fromCurrency)
        calc_result_text.text = model.value
        calc_to_link_button.drawable.setTint(ContextCompat.getColor(context, model.linkColor))
        viewIsUpdating = false
    }

    override fun setPresenter(p: CalculatorContract.Presenter) {
        this.presenter = p;
    }

    override fun showCurrencyPicker(from: Boolean, currencies: Array<String>) {
        CurrencySelector.showCurrencySelector(
                context,
                currencies,
                { _: DialogInterface, idx: Int ->
                    if (from) presenter.setCurrencyFrom(currencies.get(idx))
                    else presenter.setCurrencyTo(currencies.get(idx))
                })
    }
}