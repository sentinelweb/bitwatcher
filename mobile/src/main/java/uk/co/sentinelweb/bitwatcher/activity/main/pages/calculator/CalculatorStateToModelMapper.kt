package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import java.text.DecimalFormat
import javax.inject.Inject


class CalculatorStateToModelMapper @Inject  constructor() {
    companion object {
        val FORMATTER = DecimalFormat("###,###,###,###.0####");
    }

    fun map(state:CalculatorState):CalculatorState.CalculatorModel {
        return CalculatorState.CalculatorModel(
                state.amount.dp(4),
                FORMATTER.format(state.value),
                state.rate.dp(6),
                "${state.increment.dp(2)} ${state.incrementUnit.display()}",
                "${state.decrement.dp(2)} ${state.decrementUnit.display()}",
                state.currencyFrom.toString(),
                state.currencyTo.toString(),
                if (state.linkToRate) R.color.grey_700 else R.color.grey_300
        )
    }
}
