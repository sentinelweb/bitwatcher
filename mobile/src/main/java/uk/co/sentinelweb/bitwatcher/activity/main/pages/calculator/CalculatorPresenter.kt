package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.common.preference.BitwatcherPreferences
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.mappers.CurrencyListGenerator
import uk.co.sentinelweb.use_case.TickerUseCase
import java.math.BigDecimal
import javax.inject.Inject


class CalculatorPresenter @Inject constructor(
        private val view: CalculatorContract.View,
        private val preferences: BitwatcherPreferences,
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
        val oldState: BitwatcherPreferences.CalculatorStatePreferences = preferences.getLastCalculatorState()
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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        init()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        cleanup()
    }
    override fun onEnter() {

    }

    override fun onExit() {
        view.hideKeyBoard()
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

    override fun onIncrement() {
    }

    override fun onDecrement() {

    }

    override fun onRateChanged(value: String) {
        try {
            state.rate = BigDecimal(value)
        } catch(nf:NumberFormatException) {
            state.rate = BigDecimal.ZERO
        }
        state.linkToRate = false
        calculate()
        updateView(CalculatorState.Field.RATE)
    }

    override fun onAmountChanged(value: String) {
        try {
            state.amount = BigDecimal(value)
        } catch(nf:NumberFormatException) {
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

    private fun updateView(exclude:CalculatorState.Field = CalculatorState.Field.NONE) {
        view.setData(displayMapper.map(state), exclude)
    }

    private fun loadRate() {
        if (state.currencyFrom != CurrencyCode.NONE && state.currencyTo != CurrencyCode.NONE) {
            subscriptions.add(
                    tickerUseCase.getRate(state.currencyFrom, state.currencyTo)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ rate ->
                                state.rate = rate
                                calculate()
                                updateView()
                            },
                                    { error -> Log.d(TAG, "Error gettting ticker", error) })
            )
        } else {
            state.rate = BigDecimal.ZERO
            calculate()
            updateView()
        }
    }

}