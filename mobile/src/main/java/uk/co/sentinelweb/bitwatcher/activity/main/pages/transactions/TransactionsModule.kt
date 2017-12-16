package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivity
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Module(includes = arrayOf(TransactionsModule.Bindings::class))
class TransactionsModule {

    @Provides
    @PageScope
    fun provideView(activity: MainActivity): TransactionsContract.View = TransactionsView(activity)

    @Module
    interface Bindings {

        @Binds
        @PageScope
        fun bindPresenter(presenter: TransactionsPresenter): TransactionsContract.Presenter

    }
}