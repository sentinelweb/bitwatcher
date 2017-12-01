package uk.co.sentinelweb.bitwatcher.activity.pages.home

import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Subcomponent(modules = arrayOf(HomeModule::class))
@PageScope
interface HomeComponent {

    fun provideHomePresenter():HomeContract.Presenter

    @Subcomponent.Builder
    interface Builder {

        fun build(): HomeComponent
    }
}