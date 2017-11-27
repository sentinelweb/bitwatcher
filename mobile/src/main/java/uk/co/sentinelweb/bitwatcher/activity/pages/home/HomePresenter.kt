package uk.co.sentinelweb.bitwatcher.activity.pages.home

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.TickerData
import uk.co.sentinelweb.bitwatcher.domain.Transaction
import uk.co.sentinelweb.bitwatcher.net.NetModule
import uk.co.sentinelweb.bitwatcher.net.TickerDataApiInteractor
import uk.co.sentinelweb.bitwatcher.net.gdax.GdaxService
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class HomePresenter @Inject constructor(
        val homeView: HomeContract.View,
        @Named(NetModule.BITSTAMP) val tickerBitstampInteractor: TickerDataApiInteractor,
        @Named(NetModule.COINFLOOR) val tickerCoinfloorInteractor: TickerDataApiInteractor
) : HomeContract.Presenter {

    val model: HomeModel = HomeModel("BTC", "ETH")
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
        val tickers = Observable.merge(
                tickerBitstampInteractor.getTickers(
                        listOf(CurrencyCode.BTC, CurrencyCode.ETH),
                        listOf(CurrencyCode.USD, CurrencyCode.EUR)),
                tickerCoinfloorInteractor.getTickers(
                        listOf(CurrencyCode.BTC, CurrencyCode.BCH),
                        listOf(CurrencyCode.GBP))
        )

        tickers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    when (t.currencyCode) {
                        CurrencyCode.ETH ->
                            when (t.baseCurrencyCode) {
                                CurrencyCode.USD -> model.ethUsdPriceText = t.last.toString()
                                CurrencyCode.EUR -> model.ethEurPriceText = t.last.toString()
                                CurrencyCode.GBP -> model.ethGbpPriceText = t.last.toString()
                                else -> {
                                }
                            }
                        CurrencyCode.BTC ->
                            when (t.baseCurrencyCode) {
                                CurrencyCode.USD -> model.btcUsdPriceText = t.last.toString()
                                CurrencyCode.EUR -> model.btcEurPriceText = t.last.toString()
                                CurrencyCode.GBP -> model.btcGbpPriceText = t.last.toString()
                                else -> {
                                }
                            }
                        CurrencyCode.BCH ->
                            when (t.baseCurrencyCode) {
                                CurrencyCode.USD -> model.bchUsdPriceText = t.last.toString()
                                CurrencyCode.EUR -> model.bchEurPriceText = t.last.toString()
                                CurrencyCode.GBP -> model.bchGbpPriceText = t.last.toString()
                                else -> {
                                }
                            }
                        else -> {
                        }
                    }
                    homeView.setData(model)
                }, { e -> Log.d("HomePresenter", "error updating ticker data", e) }
                )


    }

    private fun startTimerInterval() {
        subscription.add(Observable.interval(1, TimeUnit.SECONDS)
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
