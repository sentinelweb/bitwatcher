package uk.co.sentinelweb.bitwatcher.activity.pages.trade

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.MainActivity
import uk.co.sentinelweb.bitwatcher.activity.pages.PageScope

@Module(includes = arrayOf(TradeModule.Bindings::class))
class TradeModule {

    @Provides
    @PageScope
    fun provideView(activity: MainActivity): TradeContract.View = TradeView(activity)

    @Module
    interface Bindings {

        @Binds
        @PageScope
        fun bindPresenter(presenter: TradePresenter): TradeContract.Presenter

    }
}