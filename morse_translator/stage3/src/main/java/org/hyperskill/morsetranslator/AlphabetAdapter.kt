package org.hyperskill.morsetranslator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.hyperskill.morsetranslator.databinding.ItemAlphabetBinding


class AlphabetAdapter : ListAdapter<Map.Entry<String, String>, AlphabetAdapter.ViewHolder>(AlphabetDiffCallback()) {

    inner class ViewHolder(private val binding: ItemAlphabetBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Map.Entry<String, String>) {
            binding.tvLetter.text = item.key
            binding.tvMorse.text = item.value
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlphabetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class AlphabetDiffCallback : DiffUtil.ItemCallback<Map.Entry<String, String>>() {
        override fun areItemsTheSame(oldItem: Map.Entry<String, String>, newItem: Map.Entry<String, String>): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Map.Entry<String, String>, newItem: Map.Entry<String, String>): Boolean {
            return oldItem == newItem
        }
    }

}