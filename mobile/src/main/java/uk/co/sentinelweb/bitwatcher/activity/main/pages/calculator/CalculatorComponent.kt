package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Subcomponent(modules = arrayOf(CalculatorModule::class))
@PageScope
interface CalculatorComponent {

    fun provideCalcPresenter(): CalculatorContract.Presenter

    @Subcomponent.Builder
    interface Builder {

        fun build(): CalculatorComponent
    }
}