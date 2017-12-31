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
import uk.co.sentinelweb.bitwatcher.common.preference.TransactionFilterInteractor
import uk.co.sentinelweb.use_case.GetTransactionsUseCase
import java.util.*
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(
        private val view: TransactionsContract.View,
        private val getTransactionUseCase: GetTransactionsUseCase,
        private val state: TransactionsState,
        filterPresenterFactory: TransactionFilterPresenterFactory,
        private val preferences: TransactionFilterInteractor
) : TransactionsContract.Presenter, TransactionFilterContract.Interactions {

    companion object {
        val TAG = TransactionsPresenter::class.java.simpleName
    }

    private val subscriptions = CompositeDisposable()

    private val listPresenter: TransactionListContract.Presenter
    private val filterPresenter: TransactionFilterContract.Presenter

    init {
        listPresenter = view.getListPresenter()
        filterPresenter = view.getFilterPresenter(filterPresenterFactory)
        filterPresenter.setInteractions(this)
        filterPresenter.init()
    }

    override fun init() {
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

    override fun cleanup() {
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
        cleanup()
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
    }

}
