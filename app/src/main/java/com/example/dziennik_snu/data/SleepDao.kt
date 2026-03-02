package com.example.dziennik_snu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sleepEntry: SleepEntry)

    // We will add a query to get all entries later
    // @Query("SELECT * FROM sleep_entries ORDER BY date DESC")
    // fun getAllEntries(): Flow<List<SleepEntry>>
}
