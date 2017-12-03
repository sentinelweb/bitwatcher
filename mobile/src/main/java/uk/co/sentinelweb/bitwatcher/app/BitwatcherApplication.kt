package uk.co.sentinelweb.bitwatcher.app

import android.app.Application
import android.util.Log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.common.database.test.DbInitialiser
import uk.co.sentinelweb.bitwatcher.common.database.test.DbMemoryInitialiser
import uk.co.sentinelweb.bitwatcher.receiver.AlarmReceiver
import javax.inject.Inject


class BitwatcherApplication : Application() {
    companion object {
        val TAG = BitwatcherApplication::class.java.simpleName
    }
    lateinit var component: BitwatcherAppComponent

    @Inject lateinit var dbInit: DbInitialiser
    @Inject lateinit var dbMemInit: DbMemoryInitialiser
    @Inject lateinit var alarm: AlarmReceiver

    override fun onCreate() {
        super.onCreate()
        component = DaggerBitwatcherAppComponent.builder().appContext(this).build()
        component.inject(this)

        // TODO remove test code
        dbInit.init().subscribeOn(Schedulers.computation()).subscribe()
        dbMemInit.init().subscribeOn(Schedulers.computation()).subscribe()

        alarm.setAlarm(this, AlarmReceiver.INTERVAL_SECS, 0 )

        // RxJava 2 will throw emitted error if the Observable is already disposed
        // this error handler logs and swallows the error for this case
        RxJavaPlugins.setErrorHandler {
            e ->
            if(e is UndeliverableException) Log.d(TAG,"RxJava Undeliverable exception",e)
            else throw e
        }
    }
}