package io.github.garykam.pasttext.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.garykam.pasttext.data.local.DateConverter
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class PastText(
    @PrimaryKey
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