package com.example.myapplication.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.response.QuizItem
import com.example.myapplication.databinding.ListHistoryBinding

class HistoryAdapter : ListAdapter<QuizItem, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: HistoryAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: HistoryAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class MyViewHolder(private val binding: ListHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuizItem) {
            binding.tvHistory.text = item.type
            binding.tvDate.text = item.createdAt
            binding.tvScore.text = item.score
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: QuizItem) {
        }
    }

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