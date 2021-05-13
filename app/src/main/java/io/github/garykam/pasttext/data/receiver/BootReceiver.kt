package io.github.garykam.pasttext.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import io.github.garykam.pasttext.PastTextApplication
import io.github.garykam.pasttext.data.model.PastText
import io.github.garykam.pasttext.data.service.AlarmHelper

class BootReceiver : BroadcastReceiver() {
    /**
     * Any pending alarms are canceled when the device shuts down.
     * Start up the alarms again after the device is rebooted.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val app = context!!.applicationContext as PastTextApplication
            val liveData = app.repository.pastTexts.asLiveData()
            val observer = object : Observer<List<PastText>> {
                override fun onChanged(data: List<PastText>) {
                    AlarmHelper.resumeAlarms(context, data)

                    liveData.removeObserver(this)
                }
            }

            liveData.observeForever(observer)
        }
    }
}
