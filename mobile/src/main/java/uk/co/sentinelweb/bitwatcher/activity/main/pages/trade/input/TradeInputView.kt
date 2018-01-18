package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.input

import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.main_calc_page.view.*
import kotlinx.android.synthetic.main.view_trade_container.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.ui.CurrencySelector
import uk.co.sentinelweb.bitwatcher.common.ui.listener.EditTextValueSliderTouchListener

class TradeInputView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), TradeInputContract.View {
    private lateinit var presenter: TradeInputContract.Presenter
    private var viewIsUpdating = false

    init {
        LayoutInflater.from(context).inflate(R.layout.view_trade_container, this, true)
        trade_execute_button.setOnClickListener({ presenter.onExecuteButtonClick() })
        trade_amount_currency_button.setOnClickListener({ presenter.onCurrencyButtonClick() })
        trade_amount_edit_view.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!viewIsUpdating) {
                    presenter.onAmountChanged(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        trade_price_edit_view.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!viewIsUpdating) {
                    presenter.onPriceChanged(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {   }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {    }
        })
        trade_price_edit_view.setOnTouchListener(EditTextValueSliderTouchListener(calc_rate_edit_value))
        trade_amount_edit_view.setOnTouchListener(EditTextValueSliderTouchListener(calc_amount_edit_value, 2))
        trade_link_button.setOnClickListener { _ -> presenter.toggleLinkCurrentPrice() }
    }

    override fun setPresenter(tradeInputPresenter: TradeInputContract.Presenter) {
        presenter = tradeInputPresenter
    }

    override fun setData(model: TradeInputState.TradeInputDisplayModel) {
        buy_amount_text.text = model.amountTrade
        trade_input_amount_help.text = model.amountHelp
        trade_execute_button.text = model.executeButtonLabel
        trade_amount_edit_view.setText(model.amount)
        trade_price_edit_view.setText(model.price)
        // TODO price link button
    }

    override fun showCurrencySelector(currencies: Array<String>) {
        CurrencySelector.showCurrencySelector(
                context,
                currencies,
                { _: DialogInterface, idx: Int -> presenter.onAmountCurrencySelected(currencies.get(idx)) })
    }

}