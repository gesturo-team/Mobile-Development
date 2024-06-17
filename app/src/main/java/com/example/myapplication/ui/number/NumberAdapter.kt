package com.example.myapplication.ui.number

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.Pair
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.response.NumberListItem
import com.example.myapplication.databinding.GridItemBinding

class NumberAdapter : ListAdapter<NumberListItem, NumberAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
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
        if (item!=null) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
                val detailIntent =
                    Intent(holder.itemView.context, DetailNumberActivity::class.java).also {
                        it.putExtra(DetailNumberActivity.EXTRA_ID, item.id)
                        it.putExtra(DetailNumberActivity.EXTRA_NAME, item.value)
                        it.putExtra(DetailNumberActivity.EXTRA_PICT, item.urlImage)
                    }
                holder.itemView.context.startActivity(detailIntent)
            }
        }
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