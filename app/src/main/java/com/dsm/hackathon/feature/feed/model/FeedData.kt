package com.dsm.hackathon.feature.feed.model

data class FeedData(
    val id: Long,
    val title: String,
    val content: String,
    val createDate: String,
    val userName: String,
    val likeCount: Int,
    val isMine: Boolean,
    var isLiked: Boolean
)
