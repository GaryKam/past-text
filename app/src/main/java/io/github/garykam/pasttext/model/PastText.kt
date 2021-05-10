package io.github.garykam.pasttext.model

import java.util.*

data class PastText(
    val title: String,
    val content: String,
    val unlockDate: Date
) {
    fun isUnlocked(): Boolean {
        return unlockDate.time < System.currentTimeMillis()
    }
}