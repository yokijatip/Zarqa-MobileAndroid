package com.gity.myzarqa.domain.repository

import com.gity.myzarqa.data.remote.dto.AuthResponse
import com.gity.myzarqa.data.remote.dto.AuthResponseCheckEmail
import com.gity.myzarqa.data.remote.dto.AuthResponseVerifyOTP
import com.gity.myzarqa.helper.ResourceHelper
import kotlinx.coroutines.flow.Flow

// Interface Auth Repository
interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<ResourceHelper<AuthResponse>>
    suspend fun register(name: String, email: String, password: String): Flow<ResourceHelper<AuthResponse>>
    suspend fun checkEmail(email: String): Flow<ResourceHelper<AuthResponseCheckEmail>>
    suspend fun checkOTP(otp: String): Flow<ResourceHelper<AuthResponseVerifyOTP>>
    suspend fun newPassword(tokenTemporary: String, newPassword: String): Flow<ResourceHelper<AuthResponse>>
}