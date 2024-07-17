package com.dsm.hackathon.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.dsm.hackathon.databinding.ItemHomeRecyclerBinding
import com.dsm.hackathon.feature.home.model.HomeData

class HomeAdapter(private val homeList: List<HomeData>, private val homeClickListener: HomeClickListener) : Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int = homeList.size
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(homeList[position])
        holder.itemView.setOnClickListener {
            homeClickListener.onHomeClicked(homeList[position])
        }
    }

    interface HomeClickListener {
        fun onHomeClicked(data: HomeData)
    }
}