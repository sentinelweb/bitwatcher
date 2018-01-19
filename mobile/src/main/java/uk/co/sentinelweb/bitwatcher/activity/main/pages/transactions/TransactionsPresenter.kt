package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterContract
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter.TransactionFilterPresenterFactory
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionItemModel
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListContract
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.bitwatcher.common.preference.TransactionFilterInteractor
import uk.co.sentinelweb.domain.TransactionItemDomain
import uk.co.sentinelweb.use_case.GetTransactionsUseCase
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(
        private val view: TransactionsContract.View,
        private val getTransactionUseCase: GetTransactionsUseCase,
        private val state: TransactionsState,
        filterPresenterFactory: TransactionFilterPresenterFactory,
        private val preferences: TransactionFilterInteractor
) : TransactionsContract.Presenter, TransactionFilterContract.Interactions, TransactionListContract.Interactions {

    companion object {
        val TAG = TransactionsPresenter::class.java.simpleName
    }

    private val subscriptions = CompositeDisposable()

    private val listPresenter: TransactionListContract.Presenter
    private val filterPresenter: TransactionFilterContract.Presenter

    init {
        listPresenter = view.getListPresenter()
        listPresenter.setInteractions(this)
        filterPresenter = view.getFilterPresenter(filterPresenterFactory)
        filterPresenter.setInteractions(this)
        filterPresenter.init()
    }

    override fun onCreate() {
        state.transactionList.clear()
        state.accountList.clear()
        state.filter = preferences.getTransactionFilter("last")
        view.showLoading(true)
        subscriptions.add(
                getTransactionUseCase
                        .getAllTransactionsByAccount()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ account ->
                            // onNext
                            state.accountList.add(account)
                            updateViewListData()
                        }, { e ->
                            // onError
                            Log.d(TAG, "error loading transactions", e)
                            view.showLoading(false)
                        }, {
                            view.showLoading(false)
                        }))
    }

    override fun onDestroy() {
        subscriptions.clear()
    }

    override fun view(): View {
        return view as View
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        preferences.saveTransactionFilter("last", filterPresenter.getFilter())
        onDestroy()
    }

    override fun onEnter() {

    }

    override fun onExit() {
        preferences.saveTransactionFilter("last", filterPresenter.getFilter())

    }

    // filter interactions
    override fun onClear() {
        state.filter = null
        view.closeFilter()
        updateViewListData()
    }

    override fun onApply() {
        state.filter = filterPresenter.getFilter()
        view.closeFilter()
        updateViewListData()
    }

    private fun updateViewListData() {
        state.transactionList.clear()
        state.accountList.forEach { account ->
            account.tranasactions.forEach({ transaction ->
                if (state.filter?.match(account, transaction) ?: true) {
                    state.transactionList.add(TransactionItemModel(transaction, account))
                }
            })
        }
        Collections.sort(state.transactionList, object : Comparator<TransactionItemModel> {
            override fun compare(p0: TransactionItemModel, p1: TransactionItemModel): Int {
                return p1.domain.date.compareTo(p0.domain.date)
            }
        })

        listPresenter.bindData(state.transactionList)
        state.summary = makeSelectionSummary(state.transactionList, "All")
        view.setData(mapModel())
    }

    override fun onSelectionChanged(selection: Set<TransactionItemModel>) {
        updateSummary(selection)
        view.setData(mapModel())
    }

    private fun updateSummary(selection: Set<TransactionItemModel>) {
        if (selection.size > 0) {
            state.summary = makeSelectionSummary(selection, "Selection")
        } else {
            state.summary = makeSelectionSummary(state.transactionList, "All")
        }
    }

    // TODO split by market (& account?)
    fun makeSelectionSummary(selection:Collection<TransactionItemModel>, prefix:String):String {
        var loopTotal = BigDecimal.ZERO
        var feesTotal = BigDecimal.ZERO
        selection.forEach({transaction ->
            when (transaction.domain) {
                is TransactionItemDomain.TradeDomain ->  {
                    val amount = transaction.domain.price * transaction.domain.amount
                    if (transaction.domain.type == TransactionItemDomain.TradeDomain.TradeType.BID) {
                        loopTotal -= amount
                    } else {
                        loopTotal += amount
                    }
                    feesTotal += transaction.domain.feesAmount
                }
            }

        })
        return "${prefix}: ${loopTotal.dp(2)} Fees: ${feesTotal.dp(2)}"
    }

    fun mapModel() : TransactionsState.TransactionsDisplayModel{
        return TransactionsState.TransactionsDisplayModel(
                state.summary
        )
    }

}
