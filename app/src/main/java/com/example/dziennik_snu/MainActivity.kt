package com.example.dziennik_snu

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dziennik_snu.data.SleepEntry
import com.example.dziennik_snu.ui.SleepViewModel
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var editTextDate: TextInputEditText
    private lateinit var editTextBedTime: TextInputEditText
    private lateinit var editTextTimeInBed: TextInputEditText
    private lateinit var editTextSleepTime: TextInputEditText
    private lateinit var editTextAwakenings: TextInputEditText
    private lateinit var editTextAwakeningsDuration: TextInputEditText
    private lateinit var editTextWakeUpTime: TextInputEditText
    private lateinit var editTextNaps: TextInputEditText
    private lateinit var editTextCoffees: TextInputEditText
    private lateinit var editTextExercise: TextInputEditText
    private lateinit var editTextAlcohol: TextInputEditText
    private lateinit var sliderSleepQuality: Slider
    private lateinit var sliderMorningFeeling: Slider
    private lateinit var sliderYesterdayFeeling: Slider
    private lateinit var buttonSave: Button
    private lateinit var buttonHistory: Button

    private val calendar = Calendar.getInstance()
    private val sleepViewModel: SleepViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupDatePicker()
        setupTimePicker(editTextBedTime)
        setupTimePicker(editTextWakeUpTime)
        setupSaveButton()
        setupHistoryButton()
    }

    private fun initializeViews() {
        editTextDate = findViewById(R.id.editTextDate)
        editTextBedTime = findViewById(R.id.editTextBedTime)
        editTextTimeInBed = findViewById(R.id.editTextTimeInBed)
        editTextSleepTime = findViewById(R.id.editTextSleepTime)
        editTextAwakenings = findViewById(R.id.editTextAwakenings)
        editTextAwakeningsDuration = findViewById(R.id.editTextAwakeningsDuration)
        editTextWakeUpTime = findViewById(R.id.editTextWakeUpTime)
        editTextNaps = findViewById(R.id.editTextNaps)
        editTextCoffees = findViewById(R.id.editTextCoffees)
        editTextExercise = findViewById(R.id.editTextExercise)
        editTextAlcohol = findViewById(R.id.editTextAlcohol)
        sliderSleepQuality = findViewById(R.id.sliderSleepQuality)
        sliderMorningFeeling = findViewById(R.id.sliderMorningFeeling)
        sliderYesterdayFeeling = findViewById(R.id.sliderYesterdayFeeling)
        buttonSave = findViewById(R.id.buttonSave)
        buttonHistory = findViewById(R.id.buttonHistory)
    }

    private fun setupDatePicker() {
        updateDateInView()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        editTextDate.setOnClickListener {
            DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupTimePicker(editText: EditText) {
        editText.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateTimeInView(editText)
            }
            TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            saveSleepEntry()
        }
    }

    private fun setupHistoryButton() {
        buttonHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveSleepEntry() {
        val sleepEntry = SleepEntry(
            date = editTextDate.text.toString(),
            bedTime = editTextBedTime.text.toString(),
            timeInBed = editTextTimeInBed.text.toString().toIntOrNull(),
            sleepTime = editTextSleepTime.text.toString().toIntOrNull(),
            awakenings = editTextAwakenings.text.toString().toIntOrNull(),
            awakeningsDuration = editTextAwakeningsDuration.text.toString().toIntOrNull(),
            wakeUpTime = editTextWakeUpTime.text.toString(),
            naps = editTextNaps.text.toString(),
            coffees = editTextCoffees.text.toString().toIntOrNull(),
            exercise = editTextExercise.text.toString(),
            alcohol = editTextAlcohol.text.toString(),
            sleepQuality = sliderSleepQuality.value,
            morningFeeling = sliderMorningFeeling.value,
            yesterdayFeeling = sliderYesterdayFeeling.value
        )

        sleepViewModel.insert(sleepEntry)
        Toast.makeText(this, "Zapisano wpis do dziennika snu!", Toast.LENGTH_SHORT).show()
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy EEEE"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        editTextDate.setText(sdf.format(calendar.time))
    }

    private fun updateTimeInView(editText: EditText) {
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        editText.setText(sdf.format(calendar.time))
    }
}
