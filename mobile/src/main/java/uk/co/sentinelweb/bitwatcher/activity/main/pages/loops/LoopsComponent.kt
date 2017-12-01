package uk.co.sentinelweb.bitwatcher.activity.pages.loops

import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Subcomponent(modules = arrayOf(LoopsModule::class))
@PageScope
interface LoopsComponent {

    fun provideLoopsPresenter(): LoopsContract.Presenter

    @Subcomponent.Builder
    interface Builder {

        fun build(): LoopsComponent
    }
}