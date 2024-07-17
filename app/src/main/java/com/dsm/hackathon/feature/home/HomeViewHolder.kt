package com.dsm.hackathon.feature.home

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dsm.hackathon.databinding.ItemHomeDicRecyclerBinding
import com.dsm.hackathon.feature.home.model.InfoData

class HomeViewHolder(private val binding: ItemHomeDicRecyclerBinding) : ViewHolder(binding.root) {
    fun bind(homeData: InfoData) {
        binding.tvHomeItemTitle.text = homeData.title
        binding.tvHomeItemContent.text = homeData.content
    }
}