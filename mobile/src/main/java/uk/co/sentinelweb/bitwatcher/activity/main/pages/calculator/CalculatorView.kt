package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R

class CalculatorView(context: Context?): FrameLayout(context), CalculatorContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.main_calc_page, this, true)
    }

    override fun setData(model: CalculatorState.CalculatorModel) {

    }
}