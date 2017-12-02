package uk.co.sentinelweb.bitwatcher.activity.edit_account

import dagger.BindsInstance
import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.common.scope.FragmentScope

@Subcomponent(modules = arrayOf(EditAccountModule::class))
@FragmentScope
interface EditAccountComponent {

    fun inject(fragment: EditAccountFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun editAccountFragment(act: EditAccountContract.View): Builder

        fun build(): EditAccountComponent
    }
}