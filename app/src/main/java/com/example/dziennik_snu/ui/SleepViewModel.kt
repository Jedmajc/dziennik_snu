package com.example.dziennik_snu.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dziennik_snu.data.SleepDatabase
import com.example.dziennik_snu.data.SleepEntry
import kotlinx.coroutines.launch

class SleepViewModel(application: Application) : AndroidViewModel(application) {

    private val sleepDao = SleepDatabase.getDatabase(application).sleepDao()

    fun insert(sleepEntry: SleepEntry) = viewModelScope.launch {
        sleepDao.insert(sleepEntry)
    }
}
