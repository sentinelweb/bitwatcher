package uk.co.sentinelweb.bitwatcher.activity

import dagger.BindsInstance
import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.activity.pages.home.HomeComponent
import uk.co.sentinelweb.bitwatcher.activity.pages.loops.LoopsComponent
import uk.co.sentinelweb.bitwatcher.activity.pages.trade.TradeComponent

@Subcomponent(modules = arrayOf(MainActivityModule::class))
@ActivityScope
interface MainActivityComponent {
    fun inject(activity: MainActivity)

    fun homePageBuilder(): HomeComponent.Builder
    fun loopsPageBuilder(): LoopsComponent.Builder
    fun tradePageBuilder(): TradeComponent.Builder

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun mainActivity(act: MainActivity):Builder

        fun build(): MainActivityComponent
    }
}