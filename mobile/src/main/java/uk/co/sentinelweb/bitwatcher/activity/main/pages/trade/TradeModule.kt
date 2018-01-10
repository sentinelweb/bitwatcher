package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivity
import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.TransactionsState
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Module(includes = arrayOf(TradeModule.Bindings::class))
class TradeModule {

    @Provides
    @PageScope
    fun provideView(activity: MainActivity): TradeContract.View = TradeView(activity)

    @Provides
    @PageScope
    fun provideState(): TradeState = TradeState()

    @Module
    interface Bindings {

        @Binds
        @PageScope
        fun bindPresenter(presenter: TradePresenter): TradeContract.Presenter

    }
}