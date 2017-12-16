package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions

import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Subcomponent(modules = arrayOf(TransactionsModule::class))
@PageScope
interface TransactionsComponent {

    fun provideLoopsPresenter(): TransactionsContract.Presenter

    @Subcomponent.Builder
    interface Builder {

        fun build(): TransactionsComponent
    }
}