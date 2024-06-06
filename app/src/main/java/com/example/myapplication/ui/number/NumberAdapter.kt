package com.example.myapplication.ui.number

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.response.NumberListItem
import com.example.myapplication.data.response.WordListItem
import com.example.myapplication.databinding.GridItemBinding
import com.example.myapplication.ui.alphabet.AlphabetAdapter

class NumberAdapter : ListAdapter<NumberListItem, NumberAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: NumberAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: NumberAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    
    inner class MyViewHolder(private val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NumberListItem) {
            Glide.with(binding.ivImg.context)
                .load(item.urlImage)
                .into(binding.ivImg)
            binding.tvText.text = item.value
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: NumberListItem) {
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NumberListItem>() {
            override fun areItemsTheSame(oldItem: NumberListItem, newItem: NumberListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NumberListItem,
                newItem: NumberListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}