package uk.co.sentinelweb.bitwatcher.pages.home

import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.net.TickerDataApiInteractor
import java.util.concurrent.TimeUnit

class HomePresenter(
        val homeView: HomeContract.View,
        val tickerDataApiInteractor: TickerDataApiInteractor = TickerDataApiInteractor()
) : HomeContract.Presenter {
    val model: HomeModel = HomeModel("BTC", "ETH")
    private val subscription = CompositeDisposable()

    override fun destroy() {
        subscription.clear()
    }

    override fun init() {
        startTimerInterval()
    }

    override fun onStart() {
        startTimerInterval()
    }

    override fun onStop() {
        subscription.clear()
    }

    override fun view(): View {
        return homeView as View
    }

    override fun loadData() {
        tickerDataApiInteractor
                .getTicker(CurrencyCode.BTC, CurrencyCode.USD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    model.btcPriceText = "${t.last} ${t.currencyCode}-${t.baseCurrencyCode}";
                    homeView.setData(model)
                },
                        { e -> Log.d("HomePresenter", "error updating ticker data", e) })
        tickerDataApiInteractor
                .getTicker(CurrencyCode.ETH, CurrencyCode.USD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    model.ethPriceText = "${t.last} ${t.currencyCode}-${t.baseCurrencyCode}";
                    homeView.setData(model)
                },
                        { e -> Log.d("HomePresenter", "error updating ticker data", e) })

    }

    private fun startTimerInterval() {
        subscription.add(Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe({ _ -> loadData() }))
    }

}