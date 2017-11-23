package uk.co.sentinelweb.bitwatcher.pages.home

import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.net.TickerDataApiInteractor
import java.util.*
import java.util.concurrent.TimeUnit

class HomePresenter(
        val homeView: HomeContract.View,
        val tickerDataApiInteractor: TickerDataApiInteractor = TickerDataApiInteractor()
) : HomeContract.Presenter {

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
                .getTicker()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t -> homeView.setData(HomeModel("${t.last} ${t.toCurrencyCode}")) },
                        {e -> Log.d("HomePresenter","error updating ticker data",e)})

    }

    private fun startTimerInterval() {
        subscription.add(Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe({ _ -> loadData() }))
    }

}