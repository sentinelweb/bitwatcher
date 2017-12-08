package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import uk.co.sentinelweb.bitwatcher.domain.Transaction

data class CalculatorState(val transaction:List<Transaction>) {

    data class CalculatorModel(val dummy:Int) {}
}