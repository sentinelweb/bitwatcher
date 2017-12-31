package uk.co.sentinelweb.bitwatcher.activity.main

import dagger.BindsInstance
import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator.CalculatorComponent
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.HomeComponent
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.TradeComponent
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.TransactionsComponent
import uk.co.sentinelweb.bitwatcher.common.scope.ActivityScope

@Subcomponent(modules = arrayOf(MainActivityModule::class))
@ActivityScope
interface MainActivityComponent {
    fun inject(activity: MainActivity)

    fun homePageBuilder(): HomeComponent.Builder
    fun loopsPageBuilder(): TransactionsComponent.Builder
    fun tradePageBuilder(): TradeComponent.Builder
    fun calcPageBuilder(): CalculatorComponent.Builder

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun mainActivity(act: MainActivity): Builder

        fun build(): MainActivityComponent
    }
}