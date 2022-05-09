package com.example.emotioncalendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emotioncalendar.data.Emotion
import com.example.emotioncalendar.databinding.ItemEmotionBinding
import javax.inject.Inject

class EmotionListAdapter @Inject constructor() : ListAdapter<Emotion, RecyclerView.ViewHolder>(EmotionDiffCallback()) {

    lateinit var onClickEmotionListener: OnClickEmotionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(ItemEmotionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemEmotionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(emotion: Emotion) {
            binding.imageView.background = ContextCompat.getDrawable(binding.imageView.context, emotion.drawable)
            binding.root.setOnClickListener {
                if(::onClickEmotionListener.isInitialized){
                    onClickEmotionListener(emotion)
                }
            }
        }
    }

    fun interface OnClickEmotionListener {
        operator fun invoke(emotion: Emotion)
    }
}

private class EmotionDiffCallback : DiffUtil.ItemCallback<Emotion>() {

    override fun areItemsTheSame(oldItem: Emotion, newItem: Emotion): Boolean {
        return oldItem.drawable == newItem.drawable
    }

    override fun areContentsTheSame(oldItem: Emotion, newItem: Emotion): Boolean {
        return oldItem == newItem
    }
}