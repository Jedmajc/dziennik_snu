package com.example.dziennik_snu.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sleepEntry: SleepEntry)

    @Delete
    suspend fun delete(sleepEntry: SleepEntry)

    // Sortowanie po ID malejąco zapewnia, że najnowsze wpisy są na górze
    @Query("SELECT * FROM sleep_entries ORDER BY id DESC")
    fun getAllEntries(): Flow<List<SleepEntry>>
}
