package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.mappers.CurrencyListGenerator
import uk.co.sentinelweb.use_case.AccountsRepositoryUseCase

class TransactionFilterPresenter(
        private val view: TransactionFilterContract.View,
        private val state: TransactionFilterState = TransactionFilterState(),
        private val accountsRepositoryUseCase: AccountsRepositoryUseCase
) : TransactionFilterContract.Presenter {
    companion object {
        val TAG: String = TransactionFilterPresenter::class.java.simpleName
    }

    override fun getFilter(): TransactionFilterDomain {
        return mapDomain()
    }

    private fun mapDomain() = TransactionFilterDomain(state.accountId, state.filterTypeList, state.currency, state.currencyBase)

    override fun onToggleTransactionType(type: TransactionFilterDomain.TransactionFilterType) {
        if (state.filterTypeList.contains(type)) {
            state.filterTypeList.remove(type)
        } else {
            state.filterTypeList.add(type)
        }
        view.update(mapDomain())
    }

    override fun onSelectAccountButtonClick() {
        if (state.accountList == null) {

            accountsRepositoryUseCase
                    .singleAllAccounts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ accountList -> state.accountList = accountList; showAccountSelector() },
                            { e -> Log.d(TAG, "failed to load account list", e) })
        } else {
            showAccountSelector()
        }
    }

    override fun onAccountSelected(index: Int) {
        if (index > 0) {
            state.accountId = state.accountList?.get(index-1)?.id
        } else {
            state.accountId = null
        }
        view.update(mapDomain())
    }

    private fun showAccountSelector() {
        val accountNameList = mutableListOf<String>()
        accountNameList.add("none")
        state.accountList?.forEach { account ->
            accountNameList.add(account.name)
        }
        state.accountSelectionList = accountNameList.toTypedArray()
        view.showAccountSelector(state.accountSelectionList)
    }

    override fun onSaveClick() {

    }

    override fun onCurrencyButtonClick(isCurrency: Boolean) {
        view.showCurrencySelector(CurrencyListGenerator.getCurrencyArray(), isCurrency)
    }

    override fun onCurrencySelected(code: String, isCurrency: Boolean) {
        if (isCurrency) {
            state.currency = CurrencyCode.lookup(code)
        } else {
            state.currencyBase = CurrencyCode.lookup(code)
        }
        view.update(mapDomain())
    }

}
