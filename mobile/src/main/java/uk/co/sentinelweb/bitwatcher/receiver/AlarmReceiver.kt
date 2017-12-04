package uk.co.sentinelweb.bitwatcher.receiver

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.app.BitwatcherApplication
import uk.co.sentinelweb.bitwatcher.orchestrator.TickerDataOrchestrator
import java.util.*
import javax.inject.Inject

class AlarmReceiver() : BroadcastReceiver() {
    companion object {
        val REQUEST_CODE = 4245342
        val INTERVAL_SECS = 5*60
    }

    @Inject lateinit var orchestrator:TickerDataOrchestrator


    val subscription = CompositeDisposable()

    override fun onReceive(context: Context, intent: Intent) {
        (context.applicationContext as BitwatcherApplication).component.inject(this)
        Log.d("AlarmReceiver","got alarm @ ${System.currentTimeMillis()}")

        subscription
                .add(orchestrator.downloadTickerToDatabase()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe({ _ -> Log.d("HomePresenter", "updated ticker data") },
                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))
    }

    fun setAlarm(c: Context, repeatSec: Int, startInSec: Int) {
        val intent = Intent(c, AlarmReceiver::class.java)
        val pendingIntent =
                PendingIntent.getBroadcast(c, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val cal = Calendar.getInstance()
        cal.add(Calendar.SECOND, startInSec)

        val am = c.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), repeatSec * 1000L,
                pendingIntent)
    }

    fun stopAlarm(c: Context) {
        val intent = Intent(c, AlarmReceiver::class.java)
        val pendingIntent =
                PendingIntent.getBroadcast(c, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val am = c.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        am.cancel(pendingIntent)
    }
}