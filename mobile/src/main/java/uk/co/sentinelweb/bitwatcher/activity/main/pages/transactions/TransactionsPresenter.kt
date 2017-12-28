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
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionListContract
import uk.co.sentinelweb.domain.TransactionItemDomain
import uk.co.sentinelweb.use_case.GetTransactionsUseCase
import java.util.*
import javax.inject.Inject


class TransactionsPresenter @Inject constructor(
        private val view: TransactionsContract.View,
        private val getTransactionUseCase: GetTransactionsUseCase,
        private val state: TransactionsState,
        filterPresenterFactory: TransactionFilterPresenterFactory
) : TransactionsContract.Presenter {

    companion object {
        val TAG = TransactionsPresenter::class.java.simpleName
    }

    private val subscriptions = CompositeDisposable()
    private val listPresenter: TransactionListContract.Presenter
    private val filterPresenter: TransactionFilterContract.Presenter

    init {
        listPresenter = view.getListPresenter()
        filterPresenter = view.getFilterPresenter(filterPresenterFactory)
    }

    override fun init() {
        state.transactionList.clear()
        state.accountList.clear()
        view.showLoading(true)
        subscriptions.add(
                getTransactionUseCase
                        .getAllTransactionsByAccount()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ account ->
                            // onNext
                            state.accountList.add(account)
                            state.transactionList.addAll(account.tranasactions)
                            Collections.sort(state.transactionList, object : Comparator<TransactionItemDomain> {
                                override fun compare(p0: TransactionItemDomain, p1: TransactionItemDomain): Int {
                                    return p1.date.compareTo(p0.date)
                                }
                            })
                            listPresenter.bindData(state.transactionList)
                            view.showLoading(false)
                        }, { e ->
                            // onError
                            Log.d(TAG, "error loading transactions", e)
                            view.showLoading(false)
                        }))
    }

    override fun cleanup() {
        subscriptions.clear()
    }

    override fun view(): View {
        return view as View
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {

    }

    override fun loadData() {

    }

    override fun onEnter() {
    }

    override fun onExit() {
    }

}