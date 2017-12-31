package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import uk.co.sentinelweb.bitwatcher.common.preference.CalculatorStateInteractor.CalculatorStatePreferences
import javax.inject.Inject

class CalculatorStateToPreferenceMapper @Inject constructor() {
    fun mapStateToPreferences(state: CalculatorState): CalculatorStatePreferences {
        return CalculatorStatePreferences(
                state.amount,
                state.rate,
                state.currencyFrom,
                state.currencyTo,
                state.linkToRate
        )
    }

    fun mapPreferencesToState(oldState: CalculatorStatePreferences, state: CalculatorState) {
        state.amount = oldState.amount
        state.currencyFrom = oldState.currencyFrom
        state.currencyTo = oldState.currencyTo
        state.rate = oldState.rate
        state.linkToRate = oldState.linkToRate
    }
}