package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import uk.co.sentinelweb.domain.TransactionFilterDomain

interface TransactionFilterContract {
    interface View {
        fun update(model: TransactionFilterModel)
        fun showAccountSelector(array:Array<String>?)
        fun showCurrencySelector(array:Array<String>, isFrom:Boolean)
        fun setFilterPresenter(presenter:Presenter)
        fun showFilterSelector(list: List<String>)
        fun showSaveFilterDialog(filterName: String?)
        fun showDatePicker(y: Int, m: Int, d: Int, min: Boolean)
    }

    interface Presenter {
        fun init()
        fun getFilter(): TransactionFilterDomain
        fun onToggleTransactionType(type: TransactionFilterDomain.TransactionFilterType)
        fun onAccountSelected(index:Int)
        fun onSelectAccountButtonClick()
        fun onSaveClick()
        fun onClearClick()
        fun onCurrencyButtonClick(isCurrency: Boolean)
        fun onCurrencySelected(code: String, isFrom: Boolean)
        fun setInteractions(interactions:Interactions)
        fun onApplyClick()
        fun onListClick()
        fun saveFilter(name: String)
        fun loadFilter(name: String)
        fun onDeleteClick()
        fun onDateClick(min: Boolean)
        fun onAmountChanged(numberString: String?, min: Boolean)
        fun setDate(y: Int, m: Int, d: Int, min: Boolean)
        fun clearDate(min: Boolean)
    }

    interface Interactions {
        fun onClear()
        fun onApply()
    }
}