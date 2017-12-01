package uk.co.sentinelweb.bitwatcher.activity.pages.home

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.common.database.BitwatcherMemoryDatabase
import uk.co.sentinelweb.bitwatcher.common.database.mapper.TickerEntityToDomainListMapper
import uk.co.sentinelweb.bitwatcher.orchestrator.TickerDataOrchestrator
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val homeView: HomeContract.View,
        private val state: HomeState,
        private val tickerModelMapper: TickerStateMapper,
        private val tickerEntityMapper: TickerEntityToDomainListMapper,
        private val dbMem: BitwatcherMemoryDatabase,
        private val db: BitwatcherDatabase,
        private val orchestrator: TickerDataOrchestrator
) : HomeContract.Presenter {

    private val subscription = CompositeDisposable()

    override fun init() {
        startTimerInterval()
        subscription.add(dbMem.fullAccountDao()
                .flowFullAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list -> homeView.setAccounts(list) }))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        init()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        cleanup()
    }

    override fun cleanup() {
        subscription.clear()
    }

    override fun view(): View {
        return homeView as View
    }

    private fun startTimerInterval() {

        subscription.add(Observable.interval(20, TimeUnit.SECONDS)
                .flatMap({ l -> orchestrator.downloadTickerToDatabase() })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ t -> Log.d("HomePresenter", "updated ticker data ${t.currencyCode}->${t.baseCode} = ${t.amount}") },
                        { e -> Log.d("HomePresenter", "error updating ticker data", e) }))

        subscription.add(db.tickerDao()
                .flowAllTickers()
                .flatMap({ entities -> Flowable.fromIterable(tickerEntityMapper.map(entities)) })
                .doOnNext { t-> Log.d("HomePresenter","dbf: got value for ${t.currencyCode}->${t.baseCurrencyCode} = ${t.last}") }
                .map { t -> tickerModelMapper.map(t, state.tickerState) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ state -> homeView.updateTickerState(state) },
                        { e -> Log.d("HomePresenter", "error updating ticker data", e) }))

    }

}


// GDAX test - make network call in constructor so need to be wrapped in observale
//        val tickers = Observable.fromCallable(  object : Callable<TickerDataApiInteractor> {
//            override fun call(): TickerDataApiInteractor {
//                return TickerDataApiInteractor(GdaxService.Companion.GUEST)
//            }
//        }).flatMap({interactor -> interactor.getTickers(
//                listOf(CurrencyCode.BTC, CurrencyCode.ETH, CurrencyCode.BCH),
//                listOf(CurrencyCode.USD, CurrencyCode.EUR, CurrencyCode.GBP))})


//        subscription
//                .add(Observable.interval(10, TimeUnit.SECONDS)
//                        .flatMap ({ l -> mergedTicker.getMergedTickers() })
//                        .map { t -> tickerModelMapper.map(t, state.tickerState) }
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({ state -> homeView.updateTickerState(state) },
//                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))

//        subscription
//                .add(Observable.interval(10, TimeUnit.SECONDS)
//                        .flatMap ({ l -> fullAccountsObservable })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({list -> homeView.setAccounts(list)},
//                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))





