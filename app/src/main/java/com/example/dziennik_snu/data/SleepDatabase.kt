package com.example.dziennik_snu.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SleepEntry::class], version = 2, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    abstract fun sleepDao(): SleepDao

    companion object {
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getDatabase(context: Context): SleepDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SleepDatabase::class.java,
                    "sleep_database"
                )
                .fallbackToDestructiveMigration() // Resetuje bazę danych przy zmianie schematu
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
