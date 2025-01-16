package com.gity.myzarqa.data.remote.api

import com.gity.myzarqa.data.remote.dto.AuthResponse
import com.gity.myzarqa.data.remote.dto.AuthResponseCheckEmail
import com.gity.myzarqa.data.remote.dto.AuthResponseVerifyOTP
import com.gity.myzarqa.data.remote.dto.CheckEmailRequest
import com.gity.myzarqa.data.remote.dto.LoginRequest
import com.gity.myzarqa.data.remote.dto.NewPasswordRequest
import com.gity.myzarqa.data.remote.dto.OTPRequest
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

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: CheckEmailRequest
    ): Response<AuthResponseCheckEmail>

    @POST("auth/verify-otp")
    suspend fun verifyOTP(
        @Body request: OTPRequest
    ): Response<AuthResponseVerifyOTP>

    @POST("auth/reset-password")
    suspend fun newPassword(
        @Body request: NewPasswordRequest
    ): Response<AuthResponse>
}