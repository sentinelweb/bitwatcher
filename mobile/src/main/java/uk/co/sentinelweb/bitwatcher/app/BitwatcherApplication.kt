package uk.co.sentinelweb.bitwatcher.app

import android.app.Application
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.database.test.DbInitialiser
import uk.co.sentinelweb.bitwatcher.receiver.AlarmReceiver
import javax.inject.Inject

class BitwatcherApplication : Application() {

    lateinit var component: BitwatcherAppComponent

    @Inject lateinit var dbInit: DbInitialiser
    @Inject lateinit var alarm: AlarmReceiver

    override fun onCreate() {
        super.onCreate()
        component = DaggerBitwatcherAppComponent.builder().appContext(this).build()
        component.inject(this)

        // TODO remove test code
        dbInit.init().subscribeOn(Schedulers.computation()).subscribe()

        alarm.setAlarm(this, 0, AlarmReceiver.INTERVAL_SECS)
    }
}