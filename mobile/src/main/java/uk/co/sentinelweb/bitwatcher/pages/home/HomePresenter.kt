package uk.co.sentinelweb.bitwatcher.pages.home

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.net.TickerDataApiInteractor

class HomePresenter(
        val homeView: HomeContract.View,
        val tickerDataApiInteractor: TickerDataApiInteractor = TickerDataApiInteractor()
) : HomeContract.Presenter {

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun view(): View {
        return homeView as View
    }

    override fun loadData() {
        tickerDataApiInteractor
                .getTicker()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t -> homeView.setData(HomeModel("${t.last} ${t.toCurrencyCode}")) })

    }

}