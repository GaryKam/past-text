package io.github.garykam.pasttext.data.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import io.github.garykam.pasttext.data.model.PastText
import io.github.garykam.pasttext.data.receiver.AlarmReceiver
import java.util.*

object AlarmHelper {
    /**
     * Schedules an alarm for the specified date.
     */
    fun startAlarm(context: Context, date: Date) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, System.currentTimeMillis().toInt(), intent, 0)
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, date.time, alarmIntent)
    }

    /**
     * Schedules an alarm for each Past Text that is not yet unlocked.
     */
    fun resumeAlarms(context: Context, pastTexts: List<PastText>) {
        pastTexts.filter { !it.isUnlocked() }.forEach { pastText ->
            startAlarm(context, pastText.unlockDate)
        }
    }
}
