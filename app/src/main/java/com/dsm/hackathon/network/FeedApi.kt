package com.dsm.hackathon.network

import com.dsm.hackathon.feature.feed.model.FeedResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {
    @GET("feeds/app")
    fun getFeeds(@Query("sort") sort: String, @Header("X-identifier") identifier: String): Call<FeedResponse>

    @POST("likes/{feed-id}")
    fun feedLike(@Path("feed-id") id: Long, @Header("X-identifier") identifier: String): Call<Void>

    @DELETE("likes/{feed-id}")
    fun feedLikeCancel(@Path("feed-id") id: Long, @Header("X-identifier") identifier: String): Call<Void>
}