package com.example.myapplication.ui.alphabet

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.response.WordListItem
import com.example.myapplication.databinding.GridItemBinding

class AlphabetAdapter : ListAdapter<WordListItem, AlphabetAdapter.AlphabetViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class AlphabetViewHolder(private val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WordListItem) {
            Glide.with(binding.ivImg.context)
                .load(item.urlImage)
                .into(binding.ivImg)
            binding.tvText.text = item.value
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlphabetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        val item = getItem(position)
        if (item!= null) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
                val detailIntent =
                    Intent(holder.itemView.context, DetailAlphabetActivity::class.java).also {
                        it.putExtra(DetailAlphabetActivity.EXTRA_ID, item.id)
                        it.putExtra(DetailAlphabetActivity.EXTRA_NAME, item.value)
                        it.putExtra(DetailAlphabetActivity.EXTRA_PICT, item.urlImage)
                    }
                holder.itemView.context.startActivity(detailIntent)
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: WordListItem) {
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WordListItem>() {
            override fun areItemsTheSame(oldItem: WordListItem, newItem: WordListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: WordListItem,
                newItem: WordListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}