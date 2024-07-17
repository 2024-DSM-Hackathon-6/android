package com.dsm.hackathon.feature.auth.model

data class LoginRequest(
    val accountId: String,
    val password: String
)
