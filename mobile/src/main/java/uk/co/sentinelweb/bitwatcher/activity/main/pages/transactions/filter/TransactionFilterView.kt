package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.transaction_filter_bottom_sheet.view.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterDomain.TransactionFilterType.*
import uk.co.sentinelweb.bitwatcher.common.ui.CurrencySelector

class TransactionFilterView(c:Context, a:AttributeSet) : ConstraintLayout(c,a), TransactionFilterContract.View {
    lateinit var presenter: TransactionFilterContract.Presenter

    init {
        LayoutInflater.from(context).inflate(R.layout.transaction_filter_bottom_sheet, this, true)
        buy_checkbox.setOnClickListener({presenter.onToggleTransactionType(BUY)})
        sell_checkbox.setOnClickListener({presenter.onToggleTransactionType(SELL)})
        deposit_checkbox.setOnClickListener({presenter.onToggleTransactionType(DEPOSIT)})
        withdraw_checkbox.setOnClickListener({presenter.onToggleTransactionType(WITHDRAW)})
        save_button.setOnClickListener({presenter.onSaveClick()})
        account_selector_button.setOnClickListener({presenter.onSelectAccountButtonClick()})
        currency_button.setOnClickListener({presenter.onCurrencyButtonClick(true)})
        currency_base_button.setOnClickListener({presenter.onCurrencyButtonClick(false)})
    }

    override fun update(domain: TransactionFilterDomain) {
        buy_checkbox.isChecked = domain.filterTypeList.contains(BUY)
        sell_checkbox.isChecked = domain.filterTypeList.contains(SELL)
        deposit_checkbox.isChecked = domain.filterTypeList.contains(DEPOSIT)
        withdraw_checkbox.isChecked = domain.filterTypeList.contains(WITHDRAW)

        currency_base_button.text = domain.currencyBase.toString()
        currency_button.text = domain.currency.toString()

        account_selector_button.text = domain.accountId?.toString() ?: "None"
    }

    override fun showAccountSelector(array: Array<String>?) {
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_select_currency))
                .setItems(array, {_,index -> presenter.onAccountSelected(index)})
                .setCancelable(true)
                .create()
                .show()
    }

    override fun showCurrencySelector(array: Array<String>, isCurrency: Boolean) {
        CurrencySelector.showCurrencySelector(context, array, {_,index -> presenter.onCurrencySelected(array[index], isCurrency)} )
    }

    override fun setFilterPresenter(presenter: TransactionFilterContract.Presenter) {
       this.presenter = presenter
    }

}