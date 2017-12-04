package uk.co.sentinelweb.bitwatcher.activity.pages.loops

import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivity
import uk.co.sentinelweb.bitwatcher.activity.main.pages.loops.LoopsPresenter
import uk.co.sentinelweb.bitwatcher.activity.main.pages.loops.LoopsView
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

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