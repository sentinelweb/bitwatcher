package uk.co.sentinelweb.bitwatcher.activity

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.pages.home.HomeComponent
import uk.co.sentinelweb.bitwatcher.activity.pages.loops.LoopsComponent
import uk.co.sentinelweb.bitwatcher.activity.pages.trade.TradeComponent

@Module (includes = arrayOf(MainActivityModule.Bindings::class),
        subcomponents = arrayOf(HomeComponent::class, LoopsComponent::class, TradeComponent::class))
class MainActivityModule {

    @Provides
    @ActivityScope
    fun provideView(activity: MainActivity): MainContract.View = activity

    @Module
    interface Bindings {
        @Binds
        @ActivityScope
        fun bindPresenter(presenter:MainPresenter):MainContract.Presenter

    }
}