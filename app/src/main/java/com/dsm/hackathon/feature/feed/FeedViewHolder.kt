package com.dsm.hackathon.feature.feed

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.ItemFeedRecyclerBinding
import com.dsm.hackathon.feature.feed.model.FeedData

class FeedViewHolder(private val binding: ItemFeedRecyclerBinding) : ViewHolder(binding.root) {
    val context = binding.root.context
    val like = binding.ivFeedItemLike
    val likeLay = binding.layFeedItemLike
    val count = binding.tvFeedItemCount
    fun bind(data: FeedData) {
        binding.tvFeedItemWriter.text = data.userName
        binding.tvFeedItemTitle.text = data.title
        binding.tvFeedItemContent.text = data.content
        binding.tvFeedItemCount.text = data.likeCount.toString()

        if (data.isLiked) {
            binding.ivFeedItemLike.setColorFilter(context.resources.getColor(R.color.main300))
        } else {
            binding.ivFeedItemLike.setColorFilter(context.resources.getColor(R.color.gray600))
        }
    }
}