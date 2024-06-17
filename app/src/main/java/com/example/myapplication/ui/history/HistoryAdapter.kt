package com.example.myapplication.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.response.QuizItem
import com.example.myapplication.databinding.ListHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter : ListAdapter<QuizItem, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {


    inner class HistoryViewHolder(private val binding: ListHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: QuizItem) {
            binding.tvHistory.text = item.type?.capitalizeWords() + " Quiz"
            binding.tvDate.text = item.createdAt?.let { convertDate(it) }
            binding.tvScore.text = "${item.score}/10"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun convertDate(date: String): String {
        val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputDate = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
        val convertDate = inputDate.parse(date)
        return  outputDate.format(convertDate!!)

    }

    fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    } }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QuizItem>() {
            override fun areItemsTheSame(oldItem: QuizItem, newItem: QuizItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: QuizItem,
                newItem: QuizItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}