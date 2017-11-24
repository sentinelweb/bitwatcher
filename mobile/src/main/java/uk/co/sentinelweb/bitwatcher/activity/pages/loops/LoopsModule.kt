package uk.co.sentinelweb.bitwatcher.activity.pages.loops

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.MainActivity
import uk.co.sentinelweb.bitwatcher.activity.pages.PageScope

@Module(includes = arrayOf(LoopsModule.Bindings::class))
class LoopsModule {

    @Provides
    @PageScope
    fun provideView(activity: MainActivity): LoopsContract.View = LoopsView(activity)

    @Module
    interface Bindings {

        @Binds
        @PageScope
        fun bindPresenter(presenter: LoopsPresenter): LoopsContract.Presenter

    }
}