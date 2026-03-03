package com.example.dziennik_snu

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.dziennik_snu.data.SleepEntry
import com.example.dziennik_snu.ui.SleepHistoryAdapter
import com.example.dziennik_snu.ui.SleepViewModel
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private val sleepViewModel: SleepViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHistory)
        val adapter = SleepHistoryAdapter { entry ->
            showDetailsDialog(entry)
        }
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            sleepViewModel.allEntries.collect { entries ->
                adapter.submitList(entries)
            }
        }
    }

    private fun showDetailsDialog(entry: SleepEntry) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_sleep_entry_details, null)
        
        dialogView.findViewById<TextView>(R.id.detailDate).text = entry.date
        dialogView.findViewById<TextView>(R.id.detailBedTime).text = "Pójście do łóżka: ${entry.bedTime}"
        dialogView.findViewById<TextView>(R.id.detailTimeInBed).text = "Czas w łóżku: ${entry.timeInBed ?: 0} min"
        dialogView.findViewById<TextView>(R.id.detailSleepTime).text = "Czas zasypiania: ${entry.sleepTime ?: 0} min"
        dialogView.findViewById<TextView>(R.id.detailAwakenings).text = "Liczba pobudek: ${entry.awakenings ?: 0}"
        dialogView.findViewById<TextView>(R.id.detailAwakeningsDuration).text = "Długość pobudek: ${entry.awakeningsDuration ?: 0} min"
        dialogView.findViewById<TextView>(R.id.detailWakeUpTime).text = "Godzina wstania: ${entry.wakeUpTime}"
        
        dialogView.findViewById<TextView>(R.id.detailNaps).text = "Drzemki: ${entry.naps ?: "-"}"
        dialogView.findViewById<TextView>(R.id.detailCoffees).text = "Liczba kaw: ${entry.coffees ?: 0}"
        dialogView.findViewById<TextView>(R.id.detailExercise).text = "Wysiłek: ${entry.exercise ?: "-"}"
        dialogView.findViewById<TextView>(R.id.detailAlcohol).text = "Alkohol: ${entry.alcohol ?: "-"}"
        
        dialogView.findViewById<TextView>(R.id.detailSleepQuality).text = "Jakość snu: ${entry.sleepQuality}/5.0"
        dialogView.findViewById<TextView>(R.id.detailMorningFeeling).text = "Samopoczucie rano: ${entry.morningFeeling}/5.0"
        dialogView.findViewById<TextView>(R.id.detailYesterdayFeeling).text = "Samopoczucie wczoraj: ${entry.yesterdayFeeling}/5.0"

        AlertDialog.Builder(this)
            .setTitle("Szczegóły wpisu")
            .setView(dialogView)
            .setPositiveButton("Zamknij", null)
            .show()
    }
}
