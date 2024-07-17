package com.dsm.hackathon.feature.home

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dsm.hackathon.databinding.ItemHomeRecyclerBinding
import com.dsm.hackathon.feature.home.model.HomeData

class HomeViewHolder(private val binding: ItemHomeRecyclerBinding) : ViewHolder(binding.root) {
    fun bind(homeData: HomeData) {
        binding.tvHomeItemTitle.text = homeData.title
        binding.tvHomeItemContent.text = homeData.content
    }
}