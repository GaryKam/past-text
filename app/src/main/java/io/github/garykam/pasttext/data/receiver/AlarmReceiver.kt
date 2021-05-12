package io.github.garykam.pasttext.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.garykam.pasttext.data.service.NotificationHelper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        NotificationHelper.createNotification(context!!)
    }
}