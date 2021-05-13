package io.github.garykam.pasttext.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.garykam.pasttext.data.model.PastText
import kotlinx.coroutines.flow.Flow

@Dao
interface PastTextDao {
    @Query("SELECT * FROM PastText")
    fun getAll(): Flow<List<PastText>>

    @Insert
    suspend fun insert(pastText: PastText)
}
