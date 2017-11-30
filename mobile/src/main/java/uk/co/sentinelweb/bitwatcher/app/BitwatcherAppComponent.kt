package uk.co.sentinelweb.bitwatcher.app

import dagger.BindsInstance
import dagger.Component
import uk.co.sentinelweb.bitwatcher.activity.MainActivityComponent
import uk.co.sentinelweb.bitwatcher.database.BitwatcherDbModule
import uk.co.sentinelweb.bitwatcher.net.NetModule
import javax.inject.Singleton

@Component(modules = arrayOf(BitwatcherAppModule::class, NetModule::class, BitwatcherDbModule::class))
@Singleton
interface BitwatcherAppComponent {
    fun inject(app:BitwatcherApplication)

    fun mainActivityBuilder():MainActivityComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance fun appContext(app:BitwatcherApplication):Builder
        fun build():BitwatcherAppComponent
    }
}