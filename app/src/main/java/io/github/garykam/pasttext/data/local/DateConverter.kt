package io.github.garykam.pasttext.data.local

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromTimestamp(long: Long): Date = Date(long)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
}
