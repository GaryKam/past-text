package io.github.garykam.pasttext.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.garykam.pasttext.R
import io.github.garykam.pasttext.ui.MainActivity

object NotificationHelper {
    private const val CHANNEL_ID = "CHANNEL_PAST_TEXT"

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID, context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.channel_desc)
        }

        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    fun createNotification(context: Context) {
        val pendingIntent = Intent(context, MainActivity::class.java).let { intent ->
            PendingIntent.getActivity(context, 0, intent, 0)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_time)
            .setContentTitle(context.getString(R.string.past_text_unlocked))
            .setContentText(context.getString(R.string.past_text_view))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(0, notification)
    }
}