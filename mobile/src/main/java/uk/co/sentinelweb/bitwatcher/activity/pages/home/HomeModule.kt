package uk.co.sentinelweb.bitwatcher.activity.pages.home

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.MainActivity
import uk.co.sentinelweb.bitwatcher.activity.pages.PageScope

@Module(includes = arrayOf(HomeModule.Bindings::class))
class HomeModule {

    @Provides
    @PageScope
    fun provideView(activity: MainActivity) : HomeContract.View = HomeView(activity)

    @Module
    interface Bindings {
        @Binds
        @PageScope
        fun bindPresenter(presenter:HomePresenter):HomeContract.Presenter

    }
}