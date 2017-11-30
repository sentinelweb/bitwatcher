package uk.co.sentinelweb.bitwatcher.receiver

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.sentinelweb.bitwatcher.app.BitwatcherApplication
import uk.co.sentinelweb.bitwatcher.database.BitwatcherDatabase
import uk.co.sentinelweb.bitwatcher.database.mapper.TickerDomainToEntityMapper
import uk.co.sentinelweb.bitwatcher.database.test.DbInitialiser
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor
import uk.co.sentinelweb.bitwatcher.receiver.AlarmReceiver.Companion.REQUEST_CODE
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AlarmReceiver() : BroadcastReceiver() {
    companion object {
        val REQUEST_CODE = 4245342
        val INTERVAL_SECS = 5
    }

    @Inject lateinit var tickersInteractor: TickerMergeInteractor
    @Inject lateinit var db: BitwatcherDatabase
    @Inject lateinit var entityMapper: TickerDomainToEntityMapper

    val subscription = CompositeDisposable()

    override fun onReceive(context: Context, intent: Intent) {
        (context.applicationContext as BitwatcherApplication).component.inject(this)
        //Log.d("AlarmReceiver","got alarm @ ${System.currentTimeMillis()}")
        subscription
                .add(tickersInteractor.getMergedTickers()
                        .filter({t -> t != null})
                        .map { domain -> entityMapper.map(domain) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe({ entity -> db.tickerDao().updateTicker(entity.currencyCode, entity.baseCode, entity.amount, entity.dateStamp) },
                                { e -> Log.d("HomePresenter", "error updating ticker data", e) }))
    }

    fun setAlarm(c: Context, repeatSec: Int, startInSec: Int) {
        val intent = Intent(c, AlarmReceiver::class.java)
        val pendingIntent =
                PendingIntent.getBroadcast(c, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val cal = Calendar.getInstance()
        cal.add(Calendar.SECOND, startInSec)

        val am = c.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        am?.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), repeatSec * 1000L,
                pendingIntent)
    }

    fun stopAlarm(c: Context) {
        val intent = Intent(c, AlarmReceiver::class.java)
        val pendingIntent =
                PendingIntent.getBroadcast(c, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val am = c.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        am?.cancel(pendingIntent)
    }
}