package io.github.garykam.pasttext.data.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import io.github.garykam.pasttext.data.receiver.AlarmReceiver
import java.util.*

object AlarmHelper {
    fun startAlarm(context: Context, calendar: Calendar) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, alarmIntent)
    }
}