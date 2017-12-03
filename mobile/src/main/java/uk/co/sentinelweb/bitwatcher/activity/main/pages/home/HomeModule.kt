package uk.co.sentinelweb.bitwatcher.activity.pages.home

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivity
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.HomePresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.home.HomeView
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Module(includes = arrayOf(HomeModule.Bindings::class))
class HomeModule {

    @Provides
    @PageScope
    fun provideView(activity: MainActivity) : HomeContract.View = HomeView(activity)

    @Provides
    @PageScope
    fun provideModel() : HomeState = HomeState()

    @Module
    interface Bindings {
        @Binds
        @PageScope
        fun bindPresenter(presenter: HomePresenter):HomeContract.Presenter

    }
}