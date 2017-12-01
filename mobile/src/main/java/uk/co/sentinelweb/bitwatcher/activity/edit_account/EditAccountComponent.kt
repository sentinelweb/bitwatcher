package uk.co.sentinelweb.bitwatcher.activity.edit_account

import dagger.BindsInstance
import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.common.scope.ActivityScope
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivity
import uk.co.sentinelweb.bitwatcher.common.scope.FragmentScope

@Subcomponent(modules = arrayOf(EditAccountModule::class))
@FragmentScope
interface EditAccountComponent {
    fun inject(activity:EditAccountActivity) // possibly not needed
    fun inject(fragment:EditAccountFragment)
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun editAccountFragment(act: MainActivity):Builder

        fun build(): EditAccountComponent
    }
}