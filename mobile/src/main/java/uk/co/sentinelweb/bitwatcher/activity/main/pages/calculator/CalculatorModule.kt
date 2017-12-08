package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivity
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Module(includes = arrayOf(CalculatorModule.Bindings::class))
class CalculatorModule {

    @Provides
    @PageScope
    fun provideView(activity: MainActivity): CalculatorContract.View = CalculatorView(activity)

    @Module
    interface Bindings {

        @Binds
        @PageScope
        fun bindPresenter(presenter: CalculatorPresenter): CalculatorContract.Presenter

    }
}