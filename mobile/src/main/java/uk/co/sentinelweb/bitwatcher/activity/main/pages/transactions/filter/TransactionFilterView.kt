package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.transaction_filter_bottom_sheet.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.ui.CurrencySelector
import uk.co.sentinelweb.domain.TransactionFilterDomain.TransactionFilterType.*


class TransactionFilterView(c: Context, a: AttributeSet) : FrameLayout(c, a), TransactionFilterContract.View {
    lateinit var presenter: TransactionFilterContract.Presenter
    private var viewIsUpdating: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.transaction_filter_bottom_sheet, this, true)
        buy_checkbox.setOnClickListener({ presenter.onToggleTransactionType(BUY) })
        sell_checkbox.setOnClickListener({ presenter.onToggleTransactionType(SELL) })
        deposit_checkbox.setOnClickListener({ presenter.onToggleTransactionType(DEPOSIT) })
        withdraw_checkbox.setOnClickListener({ presenter.onToggleTransactionType(WITHDRAW) })
        save_button.setOnClickListener({ presenter.onSaveClick() })
        account_selector_button.setOnClickListener({ presenter.onSelectAccountButtonClick() })
        currency_from_button.setOnClickListener({ presenter.onCurrencyButtonClick(true) })
        currency_to_button.setOnClickListener({ presenter.onCurrencyButtonClick(false) })
        close_button.setOnClickListener({ presenter.onClearClick() })
        apply_button.setOnClickListener({ presenter.onApplyClick() })
        list_button.setOnClickListener({ presenter.onListClick() })
        delete_button.setOnClickListener({ presenter.onDeleteClick() })
        date_min_button.setOnClickListener({ presenter.onDateClick(true) })
        date_max_button.setOnClickListener({ presenter.onDateClick(false) })
        amount_min_edit_value.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!viewIsUpdating) {
                    presenter.onAmountChanged(p0.toString(), true)
                }
            }

            override fun afterTextChanged(p0: Editable) {}
            override fun beforeTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        //amount_min_edit_value.setOnTouchListener(EditTextValueSliderTouchListener(amount_min_edit_value))
        amount_max_edit_value.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!viewIsUpdating) {
                    presenter.onAmountChanged(p0.toString(), false)
                }
            }

            override fun afterTextChanged(p0: Editable) {}
            override fun beforeTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {}

        })
       // amount_max_edit_value.setOnTouchListener(EditTextValueSliderTouchListener(amount_max_edit_value))
    }

    override fun setFilterPresenter(presenter: TransactionFilterContract.Presenter) {
        this.presenter = presenter
    }

    override fun update(model: TransactionFilterModel) {
        buy_checkbox.isChecked = model.filterTypeList.contains(BUY)
        sell_checkbox.isChecked = model.filterTypeList.contains(SELL)
        deposit_checkbox.isChecked = model.filterTypeList.contains(DEPOSIT)
        withdraw_checkbox.isChecked = model.filterTypeList.contains(WITHDRAW)

        currency_to_button.text = model.currencyTo.toString()
        currency_from_button.text = model.currencyFrom.toString()

        account_selector_button.text = model.accountName
        filter_name_text.text = model.name

        delete_button.visibility = if (model.deleteVisible) View.VISIBLE else View.GONE

        viewIsUpdating = true
        amount_min_edit_value.setText(model.minAmount)
        amount_max_edit_value.setText(model.maxAmount)
        amount_min_edit_value.setEnabled(model.amountEnabled)
        amount_max_edit_value.setEnabled(model.amountEnabled)
        amount_min_edit_value.setActivated(model.amountEnabled)
        amount_max_edit_value.setActivated(model.amountEnabled)
        viewIsUpdating = false

        date_min_button.text = model.minDate
        date_max_button.text = model.maxDate

    }

    override fun showAccountSelector(array: Array<String>?) {
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_select_currency))
                .setItems(array, { _, index -> presenter.onAccountSelected(index) })
                .setCancelable(true)
                .create()
                .show()
    }

    override fun showCurrencySelector(array: Array<String>, isFrom: Boolean) {
        CurrencySelector.showCurrencySelector(context, array, { _, index -> presenter.onCurrencySelected(array[index], isFrom) })
    }

    override fun showSaveFilterDialog(filterName: String?) {
        val editText = EditText(context)
        editText.setText(filterName)
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.dialog_title_filter_name))
                .setView(editText)
                .setCancelable(true)
                .setPositiveButton(context.getString(R.string.ok), { _, _ -> presenter.saveFilter(editText.text.toString()) })
                .create()
                .show()
    }

    override fun showFilterSelector(list: List<String>) {
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_select_filter))
                .setItems(list.toTypedArray(), { _, index -> presenter.loadFilter(list[index]) })
                .setCancelable(true)
                .create()
                .show()
    }

    override fun showDatePicker(y: Int, m: Int, d: Int, min: Boolean) {
        val datePickerDialog = DatePickerDialog(context, { _, y1, m1, d1 -> presenter.setDate(y1, m1, d1, min) }, y, m, d)
        datePickerDialog.setOnCancelListener({presenter.clearDate(min)})
        datePickerDialog.show()
    }


}