package com.dsm.hackathon.network

import com.dsm.hackathon.feature.home.model.InfoData
import com.dsm.hackathon.feature.home.model.InfoResponse
import com.dsm.hackathon.feature.home.model.ModifyRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {
    @GET("infos")
    fun getInfo(@Query("name") name: String): Call<InfoResponse>

    @GET("infos/detail/{info-id}")
    fun getDetailInfo(@Path("info-id") id: Long): Call<InfoData>

    @POST("status/fix")
    fun requestModify(@Header("X-identifier") identifier: String, @Body request: ModifyRequest): Call<Void>
}