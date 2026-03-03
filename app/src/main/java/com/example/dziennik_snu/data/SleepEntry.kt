package com.example.dziennik_snu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_entries")
data class SleepEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val bedTime: String,
    val timeInBed: Int?, // in minutes
    val sleepTime: Int?, // in minutes
    val awakenings: Int?,
    val awakeningsDuration: Int?, // in minutes
    val timeInBedMorning: Int?, // NOWE POLE: in minutes
    val wakeUpTime: String,
    val naps: String?,
    val coffees: Int?,
    val exercise: String?,
    val alcohol: String?,
    val sleepQuality: Float,
    val morningFeeling: Float,
    val yesterdayFeeling: Float
)
