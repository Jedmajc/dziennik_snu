package com.example.dziennik_snu.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dziennik_snu.R
import com.example.dziennik_snu.data.SleepEntry

class SleepHistoryAdapter : ListAdapter<SleepEntry, SleepHistoryAdapter.SleepEntryViewHolder>(SleepEntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepEntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_sleep_entry, parent, false)
        return SleepEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SleepEntryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class SleepEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateView: TextView = itemView.findViewById(R.id.textViewDate)
        private val qualityView: TextView = itemView.findViewById(R.id.textViewSleepQuality)
        private val timeInBedView: TextView = itemView.findViewById(R.id.textViewTimeInBed)

        fun bind(entry: SleepEntry) {
            dateView.text = entry.date
            qualityView.text = "Jakość snu: ${entry.sleepQuality}/5.0"
            timeInBedView.text = "Czas w łóżku: ${entry.timeInBed} min"
        }
    }

    class SleepEntryDiffCallback : DiffUtil.ItemCallback<SleepEntry>() {
        override fun areItemsTheSame(oldItem: SleepEntry, newItem: SleepEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SleepEntry, newItem: SleepEntry): Boolean {
            return oldItem == newItem
        }
    }
}
