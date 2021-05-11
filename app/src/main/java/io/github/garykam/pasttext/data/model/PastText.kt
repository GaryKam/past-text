package io.github.garykam.pasttext.data.model

import java.util.*

data class PastText(
    val title: String,
    val content: String,
    val unlockDate: Date
) {
    /**
     * @return True if the Past Text can be viewed
     */
    fun isUnlocked(): Boolean {
        return unlockDate.time < System.currentTimeMillis()
    }
}