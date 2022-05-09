package com.example.emotioncalendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.emotioncalendar.data.Memo
import com.example.emotioncalendar.databinding.ItemMemoBinding
import javax.inject.Inject

class MemoListAdapter @Inject constructor() : ListAdapter<Memo, ViewHolder>(MemoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemMemoBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(memo: Memo) {
            binding.imageView.background = ContextCompat.getDrawable(binding.imageView.context, memo.emotionId)
            binding.dateView.text = memo.date
            binding.dayView.text = memo.day
            binding.contentView.text = memo.content
        }

    }

}


private class MemoDiffCallback : DiffUtil.ItemCallback<Memo>() {

    override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
        return oldItem == newItem
    }
}