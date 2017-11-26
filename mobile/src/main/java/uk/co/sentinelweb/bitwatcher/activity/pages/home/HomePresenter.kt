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
import uk.co.sentinelweb.bitwatcher.net.TickerDataApiInteractor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(
        val homeView: HomeContract.View,
        val tickerDataApiInteractor: TickerDataApiInteractor
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
        subscription.add(tickerDataApiInteractor
                .getTickers(listOf(CurrencyCode.BTC, CurrencyCode.ETH), listOf(CurrencyCode.USD, CurrencyCode.EUR))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    when (t.currencyCode) {
                        CurrencyCode.ETH ->
                            when (t.baseCurrencyCode) {
                                CurrencyCode.USD -> model.ethUsdPriceText = t.last.toString()
                                CurrencyCode.EUR -> model.ethEurPriceText = t.last.toString()
                                else -> {}
                            }
                        CurrencyCode.BTC ->
                            when (t.baseCurrencyCode) {
                                CurrencyCode.USD -> model.btcUsdPriceText = t.last.toString()
                                CurrencyCode.EUR -> model.btcEurPriceText = t.last.toString()
                                else -> {}
                            }
                        else -> {}
                    }
                    homeView.setData(model)
                }, { e -> Log.d("HomePresenter", "error updating ticker data", e) })
        )


    }

    private fun startTimerInterval() {
        subscription.add(Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe({ _ -> loadData() }))
    }

}