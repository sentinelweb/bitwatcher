package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.common.preference.TransactionFilterInteractor
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionFilterDomain
import uk.co.sentinelweb.domain.mappers.CurrencyListGenerator
import uk.co.sentinelweb.use_case.AccountsRepositoryUseCase
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class TransactionFilterPresenter(
        private val view: TransactionFilterContract.View,
        private val state: TransactionFilterState = TransactionFilterState(),
        private val accountsRepositoryUseCase: AccountsRepositoryUseCase,
        private val preferences: TransactionFilterInteractor
) : TransactionFilterContract.Presenter {
    companion object {

        val TAG: String = TransactionFilterPresenter::class.java.simpleName
        val DATE_TIME_FORMATTER = SimpleDateFormat.getDateTimeInstance() //SimpleDateFormat("dd/MM/yy HH:mm")
        val DATE_FORMATTER = SimpleDateFormat.getDateInstance() //SimpleDateFormat("dd/MM/yyyy")
        val NO_ACCOUNT = "NO ACCOUNT"
    }

    private lateinit var interactions: TransactionFilterContract.Interactions

    override fun init() {
        loadAccountList { view.update(mapModel()) }
    }

    override fun getFilter(): TransactionFilterDomain {
        return mapDomain()
    }

    override fun setInteractions(interactions: TransactionFilterContract.Interactions) {
        this.interactions = interactions
    }

    override fun onToggleTransactionType(type: TransactionFilterDomain.TransactionFilterType) {
        if (state.filterTypeList.contains(type)) {
            state.filterTypeList.remove(type)
        } else {
            state.filterTypeList.add(type)
        }
        view.update(mapModel())
    }

    override fun onSelectAccountButtonClick() {
        if (state.accountList == null) {
            loadAccountList({ showAccountSelector() })
        } else {
            showAccountSelector()
        }
    }

    override fun onAccountSelected(index: Int) {
        if (index > 0) {
            state.accountId = state.accountList?.get(index - 1)?.id
        } else {
            state.accountId = null
        }
        view.update(mapModel())
    }

    private fun showAccountSelector() {
        view.showAccountSelector(state.accountSelectionList)
    }

    override fun onCurrencyButtonClick(isCurrency: Boolean) {
        view.showCurrencySelector(CurrencyListGenerator.getCurrencyArray(true), isCurrency)
    }

    override fun onCurrencySelected(code: String, isFrom: Boolean) {
        if (isFrom) {
            state.currencyFrom = CurrencyCode.lookup(code)
        } else {
            state.currencyTo = CurrencyCode.lookup(code)
        }
        view.update(mapModel())
    }

    override fun onClearClick() {
        clearFilter()
        view.update(mapModel())
        interactions.onClear()
    }

    override fun onApplyClick() {
        interactions.onApply()
    }

    override fun onSaveClick() {
        view.showSaveFilterDialog(state.filterName)
    }

    override fun onListClick() {
        view.showFilterSelector(preferences.listTransactionFilterNames().toList())
    }

    override fun onDeleteClick() {
        val name = state.filterName
        if (name != null) {
            preferences.deleteTransactionFilter(name)
            clearFilter()
            view.update(mapModel())
        }
    }

    override fun saveFilter(name: String) {
        val update = preferences.saveTransactionFilter(name, mapDomain())
        state.saveDate = update.saveDate
        state.filterName = update.name
        view.update(mapModel())
    }

    override fun loadFilter(name: String) {
        val domain = preferences.getTransactionFilter(name)
        if (domain != null) {
            mapToState(domain)
            view.update(mapModel())
        }
    }

    override fun setDate(y: Int, m: Int, d: Int, min: Boolean) {
        val c = GregorianCalendar()
        c.set(Calendar.YEAR, y)
        c.set(Calendar.MONTH, m)
        c.set(Calendar.DAY_OF_MONTH, d)

        if (min) {
            c.set(Calendar.HOUR, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            state.minDate = c.time
        } else {
            c.set(Calendar.HOUR, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            state.maxDate = c.time
        }
        view.update(mapModel())
    }

    override fun clearDate(min: Boolean) {
        if (min) {
            state.minDate = null
        } else {
            state.maxDate = null
        }
        view.update(mapModel())
    }

    private fun clearFilter() {
        state.currencyFrom = CurrencyCode.NONE
        state.currencyTo = CurrencyCode.NONE
        state.filterTypeList.clear()
        state.accountId = null
        state.filterName = null
        state.saveDate = null
        state.minDate = null
        state.maxDate = null
        state.minAmount = null
        state.maxAmount = null

    }

    override fun onDateClick(min: Boolean) {
        val c = GregorianCalendar()
        if (min) {
            c.time = state.minDate ?: Date()
        } else {
            c.time = state.maxDate ?: Date()
        }
        view.showDatePicker(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), min)
    }

    override fun onAmountChanged(numberString: String?, min: Boolean) {
        if (numberString?.isNotEmpty() ?: false) {
            if (min) {
                state.minAmount = BigDecimal(numberString)
            } else {
                state.maxAmount = BigDecimal(numberString)
            }
        } else {
            if (min) {
                state.minAmount = null
            } else {
                state.maxAmount = null
            }
        }
    }

    private fun mapToState(domain: TransactionFilterDomain) {
        state.currencyFrom = domain.currencyFrom
        state.currencyTo = domain.currencyTo
        state.filterTypeList.clear()
        state.filterTypeList.addAll(domain.filterTypeList)
        state.accountId = domain.accountId
        state.filterName = domain.name
        state.saveDate = domain.saveDate
        state.minAmount = domain.minAmount
        state.maxAmount = domain.maxAmount
        state.minDate = domain.minDate
        state.maxDate = domain.maxDate
    }

    private fun mapDomain() = TransactionFilterDomain(
            state.accountId,
            state.filterName,
            state.filterTypeList,
            state.currencyFrom,
            state.currencyTo,
            state.saveDate,
            state.minAmount,
            state.maxAmount,
            state.minDate,
            state.maxDate
    )

    private fun mapModel(): TransactionFilterModel {
        val filter = state.accountList?.filter { account -> account.id == state.accountId }
        return TransactionFilterModel(
                if (filter?.isEmpty() ?: true) NO_ACCOUNT else filter?.first()?.name,
                state.filterName,
                state.filterTypeList,
                state.currencyFrom,
                state.currencyTo,
                if (state.saveDate != null) DATE_TIME_FORMATTER.format(state.saveDate) else null,
                state.minAmount?.toString(),
                state.maxAmount?.toString(),
                if (state.minDate != null) DATE_FORMATTER.format(state.minDate) else "*",
                if (state.maxDate != null) DATE_FORMATTER.format(state.maxDate) else "*",
                state.filterName != null,
                state.currencyFrom != CurrencyCode.NONE
        )
    }

    private fun loadAccountList(function: (() -> Unit)?) {
        accountsRepositoryUseCase
                .singleAllAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ accountList ->
                    state.accountList = accountList
                    val accountNameList = mutableListOf<String>()
                    accountNameList.add(NO_ACCOUNT)
                    state.accountList?.forEach { account ->
                        accountNameList.add(account.name)
                    }
                    state.accountSelectionList = accountNameList.toTypedArray()
                    if (function != null) {
                        function.invoke()
                    }
                }, { e -> Log.d(TAG, "failed to load account list", e) })
    }

}
