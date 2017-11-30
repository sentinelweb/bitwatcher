package uk.co.sentinelweb.bitwatcher.receiver

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import uk.co.sentinelweb.bitwatcher.app.BitwatcherApplication
import javax.inject.Inject

class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var alarm: AlarmReceiver

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            (context.applicationContext as BitwatcherApplication).component.inject(this)
            alarm.setAlarm(context, 0, AlarmReceiver.INTERVAL_SECS)
        }
    }
}