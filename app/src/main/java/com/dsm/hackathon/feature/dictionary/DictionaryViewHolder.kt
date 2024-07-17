package com.dsm.hackathon.feature.dictionary

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dsm.hackathon.databinding.ItemHomeDicRecyclerBinding
import com.dsm.hackathon.feature.home.model.InfoData

class DictionaryViewHolder(private val binding: ItemHomeDicRecyclerBinding) : ViewHolder(binding.root) {
    fun bind(data: InfoData) {
        binding.tvHomeItemTitle.text = data.title
        binding.tvHomeItemContent.text = data.content
    }
}