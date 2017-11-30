package uk.co.sentinelweb.bitwatcher.app

import android.app.Application
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.database.DbInitialiser
import javax.inject.Inject

class BitwatcherApplication : Application() {

    lateinit var component: BitwatcherAppComponent

    @Inject lateinit var dbInit: DbInitialiser

    override fun onCreate() {
        super.onCreate()
        component = DaggerBitwatcherAppComponent.builder().appContext(this).build()
        component.inject(this)

        dbInit.init().subscribeOn(Schedulers.computation()).subscribe()
    }
}