package uk.co.sentinelweb.bitwatcher.activity.main

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.HomeComponent
import uk.co.sentinelweb.bitwatcher.activity.main.pages.trade.TradeComponent
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.TransactionsComponent
import uk.co.sentinelweb.bitwatcher.common.scope.ActivityScope

@Module (includes = arrayOf(MainActivityModule.Bindings::class),
        subcomponents = arrayOf(HomeComponent::class, TransactionsComponent::class, TradeComponent::class))// TODO do i need to declare suncompoannets
class MainActivityModule {

    @Provides
    @ActivityScope
    fun provideView(activity: MainActivity): MainContract.View = activity

    @Module
    interface Bindings {
        @Binds
        @ActivityScope
        fun bindPresenter(presenter: MainPresenter): MainContract.Presenter

    }
}