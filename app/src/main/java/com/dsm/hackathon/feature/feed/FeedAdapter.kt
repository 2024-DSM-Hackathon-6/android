package com.dsm.hackathon.feature.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.dsm.hackathon.databinding.ItemFeedRecyclerBinding
import com.dsm.hackathon.feature.feed.model.FeedData

class FeedAdapter(private val feedList: List<FeedData>) : Adapter<FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)
    }

    override fun getItemCount(): Int = feedList.size
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(feedList[position])
        holder.like.setOnClickListener {

        }
    }

    interface FeedClickListener {
        fun onFeedClicked(feedId: Long)
        fun onLikeClicked(feedId: Long, isLiked: Boolean)
    }
}