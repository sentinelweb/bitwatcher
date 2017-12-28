package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

interface TransactionFilterContract {
    interface View {
        fun update(domain: TransactionFilterDomain)
        fun showAccountSelector(array:Array<String>?)
        fun showCurrencySelector(array:Array<String>,  isCurrency:Boolean)
        fun setFilterPresenter(presenter:Presenter)
    }

    interface Presenter {
        fun getFilter():TransactionFilterDomain
        fun onToggleTransactionType(type:TransactionFilterDomain.TransactionFilterType)
        fun onAccountSelected(index:Int)
        fun onSelectAccountButtonClick()
        fun onSaveClick()
        fun onCurrencyButtonClick(isCurrency: Boolean)
        fun onCurrencySelected(code: String, isCurrency: Boolean)
    }
}