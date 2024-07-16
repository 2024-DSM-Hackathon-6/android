package com.dsm.hackathon.network

import com.dsm.hackathon.feature.auth.model.LoginRequest
import com.dsm.hackathon.feature.auth.model.LoginResponse
import com.dsm.hackathon.feature.auth.model.SignupRequest
import com.dsm.hackathon.feature.auth.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>

    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}