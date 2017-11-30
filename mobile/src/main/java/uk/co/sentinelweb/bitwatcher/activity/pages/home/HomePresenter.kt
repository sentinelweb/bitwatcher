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
import uk.co.sentinelweb.bitwatcher.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.database.entities.FullAccountView
import uk.co.sentinelweb.bitwatcher.database.mapper.TickerEntityToDomainListMapper
import uk.co.sentinelweb.bitwatcher.database.mapper.TickerEntityToDomainMapper
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val homeView: HomeContract.View,
        private val state: HomeState,
        private val tickerModelMapper: TickerStateMapper,
        private val tickerEntityMapper: TickerEntityToDomainListMapper,
        private val mergedTicker: TickerMergeInteractor,
        private val db: BitwatcherDatabase
) : HomeContract.Presenter {

//    private val tickersObservable = Observable.mergeDelayError(
//            tickerBitstampInteractor.getTickers(listOf(BTC, ETH), listOf(USD, EUR)),
//            tickerCoinfloorInteractor.getTickers(listOf(BTC, BCH), listOf(GBP)), // TODO crashing issue here when connection timing out
//            Observable.mergeDelayError(
//                    tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, EUR) },
//                    tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, USD) },
//                    tickerKrakenInteractor.flatMap { inter -> inter.getTicker(ETH, GBP) }
//            ))
//            .onErrorResumeNext({ t: Throwable -> null })


    private val subscription = CompositeDisposable()

    private val fullAccountsObservable = Observable.fromCallable(object : Callable<List<FullAccountView>> {
        override fun call(): List<FullAccountView> {
            return db.fullAccountDao().loadFullAccounts()
        }
    })

    override fun init() {
        startTimerInterval()
        subscription.add(db.fullAccountDao()
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
//        subscription
//                .add(Observable.interval(10, TimeUnit.SECONDS)
//                        .flatMap ({ l -> mergedTicker.getMergedTickers() })
//                        .map { t -> tickerModelMapper.map(t, state.tickerState) }
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({ state -> homeView.updateTickerState(state) },
//                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))
        subscription.add(db.tickerDao()
                .flowAllTickers()
                .flatMap ({ entities -> Observable.fromIterable(tickerEntityMapper.map(entities)) })
                .map { t -> tickerModelMapper.map(t, state.tickerState) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ state -> homeView.updateTickerState(state) },
                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))
//        subscription
//                .add(Observable.interval(10, TimeUnit.SECONDS)
//                        .flatMap ({ l -> fullAccountsObservable })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({list -> homeView.setAccounts(list)},
//                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))

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
