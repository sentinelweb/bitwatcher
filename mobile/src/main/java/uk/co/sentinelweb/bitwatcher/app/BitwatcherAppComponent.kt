package uk.co.sentinelweb.bitwatcher.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import uk.co.sentinelweb.bitwatcher.activity.MainActivityComponent
import uk.co.sentinelweb.bitwatcher.net.NetModule
import javax.inject.Singleton

@Component(modules = arrayOf(BitwatcherAppModule::class, NetModule::class))
@Singleton
interface BitwatcherAppComponent {
    fun inject(app:BitwatcherApplication)

    fun mainActivityBuilder():MainActivityComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance fun appContext(app:Application):Builder
        fun build():BitwatcherAppComponent
    }
}