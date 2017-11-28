package uk.co.sentinelweb.bitwatcher.activity.pages.home

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode.*
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.bitwatcher.net.TickerDataApiInteractor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class HomePresenter @Inject constructor(
        val homeView: HomeContract.View,
        val state: HomeState,
        @Named(NetModule.BITSTAMP) val tickerBitstampInteractor: TickerDataApiInteractor,
        @Named(NetModule.COINFLOOR) val tickerCoinfloorInteractor: TickerDataApiInteractor,
        @Named(NetModule.KRAKEN) val tickerKrakenInteractor: Observable<TickerDataApiInteractor>,
        val tickerModelMapper: TickerStateMapper
) : HomeContract.Presenter {

    private val subscription = CompositeDisposable()

    override fun init() {
        startTimerInterval()
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

    override fun loadData() {
        val tickers = Observable.mergeDelayError(
                tickerBitstampInteractor.getTickers(listOf(BTC, ETH), listOf(USD, EUR)),
                tickerCoinfloorInteractor.getTickers(listOf(BTC, BCH), listOf(GBP)),
                Observable.mergeDelayError(
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, EUR) },
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(BCH, USD) },
                        tickerKrakenInteractor.flatMap { inter -> inter.getTicker(ETH, GBP) }
                )
        )

        tickers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t -> tickerModelMapper.map(t, state.tickerState) }
                .subscribe({ state -> homeView.updateTickerState(state) },
                        { e -> Log.d("HomePresenter", "error updating ticker data", e) }
                )
    }

    private fun startTimerInterval() {
        subscription.add(Observable.interval(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> loadData() }))
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
