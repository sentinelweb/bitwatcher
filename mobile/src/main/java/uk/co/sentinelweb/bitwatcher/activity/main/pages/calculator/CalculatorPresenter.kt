package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.common.preference.CalculatorStateInteractor
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.mappers.CurrencyListGenerator
import uk.co.sentinelweb.domain.mc
import uk.co.sentinelweb.use_case.TickerUseCase
import java.math.BigDecimal
import javax.inject.Inject


class CalculatorPresenter @Inject constructor(
        private val view: CalculatorContract.View,
        private val preferences: CalculatorStateInteractor,
        private val state: CalculatorState,
        private val displayMapper: CalculatorStateToModelMapper,
        private val preferenceMapper: CalculatorStateToPreferenceMapper,
        private val tickerUseCase: TickerUseCase
) : CalculatorContract.Presenter {
    companion object {
        val TAG = CalculatorPresenter::class.java.simpleName
    }

    private val subscriptions = CompositeDisposable()

    override fun init() {
        view.setPresenter(this)
        val oldState: CalculatorStateInteractor.CalculatorStatePreferences = preferences.getLastCalculatorState()
        preferenceMapper.mapPreferencesToState(oldState, state)
        if (!state.linkToRate) {
            calculate()
            updateView()
        } else {
            loadRate()
        }
    }

    override fun cleanup() {
        subscriptions.clear()
        preferences.saveLastCalculatorState(preferenceMapper.mapStateToPreferences(state))
    }

    override fun view(): View {
        return view as View
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onStart() {
        init()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onStop() {
        cleanup()
    }

    override fun onEnter() {

    }

    override fun onExit() {
        view.hideKeyBoard()
    }

    override fun swapCurrencies() {
        val tmp = state.currencyFrom
        state.currencyFrom = state.currencyTo
        state.currencyTo = tmp
        state.amount = state.value
        if (state.rate > BigDecimal.ZERO) {
            state.rate = BigDecimal.ONE.divide(state.rate, mc)
        }
        calculate()
        updateView(CalculatorState.Field.NONE)
    }

    override fun onCurrencyFromButtonClick() {
        view.showCurrencyPicker(true, CurrencyListGenerator.getCurrencyArray())
    }

    override fun onCurrencyToButtonClick() {
        view.showCurrencyPicker(false, CurrencyListGenerator.getCurrencyArray())
    }

    override fun setCurrencyFrom(currency: String) {
        state.currencyFrom = CurrencyCode.lookup(currency)
        state.linkToRate = true
        loadRate()
    }

    override fun setCurrencyTo(currency: String) {
        state.currencyTo = CurrencyCode.lookup(currency)
        state.linkToRate = true
        loadRate()
    }


    override fun onRateChanged(value: String) {
        try {
            state.rate = BigDecimal(value)
        } catch (nf: NumberFormatException) {
            state.rate = BigDecimal.ZERO
        }
        state.linkToRate = false
        calculate()
        updateView(CalculatorState.Field.RATE)
    }

    override fun onAmountChanged(value: String) {
        try {
            state.amount = BigDecimal(value)
        } catch (nf: NumberFormatException) {
            state.amount = BigDecimal.ZERO
        }
        calculate()
        updateView(CalculatorState.Field.AMOUNT)
    }

    override fun toggleLinkRate() {
        state.linkToRate = !state.linkToRate
        if (state.linkToRate) {
            loadRate()
        } else {
            updateView()
        }
    }

    private fun calculate() {
        state.value = state.amount.multiply(state.rate)
    }

    private fun updateView(exclude: CalculatorState.Field = CalculatorState.Field.NONE) {
        view.setData(displayMapper.map(state), exclude)
    }

    private fun loadRate() {
        if (state.currencyFrom != CurrencyCode.NONE && state.currencyTo != CurrencyCode.NONE) {
            val rateSubscriber = RateSubscriber()
            tickerUseCase.getRate(state.currencyFrom, state.currencyTo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(rateSubscriber)
            subscriptions.add(rateSubscriber)

        } else {
            state.rate = BigDecimal.ZERO
            calculate()
            updateView()
        }
    }

    inner class RateSubscriber : DisposableSingleObserver<BigDecimal>() {

        override fun onSuccess(rate: BigDecimal) {
            state.rate = rate
            calculate()
            updateView()
        }

        override fun onError(exception: Throwable) {
            Log.d(TAG, "Error gettting ticker", exception)
        }

    }

}