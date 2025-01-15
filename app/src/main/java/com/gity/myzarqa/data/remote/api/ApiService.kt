package com.gity.myzarqa.data.remote.api

import com.gity.myzarqa.data.remote.dto.AuthResponse
import com.gity.myzarqa.data.remote.dto.LoginRequest
import com.gity.myzarqa.data.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>
}