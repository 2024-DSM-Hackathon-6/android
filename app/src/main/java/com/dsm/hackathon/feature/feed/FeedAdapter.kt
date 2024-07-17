package com.dsm.hackathon.feature.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.ItemFeedRecyclerBinding
import com.dsm.hackathon.feature.feed.model.FeedData
import kotlin.contracts.contract

class FeedAdapter(private var feedList: List<FeedData>, private val feedClickListener: FeedClickListener) : Adapter<FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)
    }

    override fun getItemCount(): Int = feedList.size
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(feedList[position])
        /*holder.like.setOnClickListener {
            feedList[position].isLiked = !feedList[position].isLiked
            if (feedList[position].isLiked) {
                holder.like.setColorFilter(holder.context.getColor(R.color.main300))
                holder.count.text = (holder.count.text.toString().toInt() + 1).toString()
            } else {
                holder.like.setColorFilter(holder.context.getColor(R.color.gray600))
                holder.count.text = (holder.count.text.toString().toInt() - 1).toString()
            }
            feedClickListener.onLikeClicked(feedList[position].id, feedList[position].isLiked)
        }*/
        holder.itemView.setOnClickListener {
            feedClickListener.onFeedClicked(feedList[position].id)
        }
    }

    interface FeedClickListener {
        fun onFeedClicked(feedId: Long)
        // fun onLikeClicked(feedId: Long, isLiked: Boolean)
    }
}