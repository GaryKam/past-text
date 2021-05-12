package io.github.garykam.pasttext

import android.app.Application
import io.github.garykam.pasttext.data.local.AppDatabase
import io.github.garykam.pasttext.data.repository.PastTextRepository

class PastTextApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val repository: PastTextRepository by lazy { PastTextRepository(database.pastTextDao()) }
}