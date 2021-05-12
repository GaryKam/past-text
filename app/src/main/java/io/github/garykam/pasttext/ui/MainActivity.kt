package io.github.garykam.pasttext.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.garykam.pasttext.R
import io.github.garykam.pasttext.data.service.NotificationHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationHelper.createChannel(this)
    }
}