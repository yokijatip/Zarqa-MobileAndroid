package com.gity.myzarqa.data.repository

import com.gity.myzarqa.data.remote.api.ApiService
import com.gity.myzarqa.data.remote.dto.AuthResponse
import com.gity.myzarqa.data.remote.dto.LoginRequest
import com.gity.myzarqa.data.remote.dto.RegisterRequest
import com.gity.myzarqa.domain.repository.AuthRepository
import com.gity.myzarqa.helper.ResourceHelper
import com.gity.myzarqa.helper.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService /* Di Injeksi dari NetworkModule */, private val sessionManager: SessionManager): AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<ResourceHelper<AuthResponse>> = flow {
        emit(ResourceHelper.Loading())
        try {
            Timber.d("Attempting login for email: $email")
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    if (authResponse.status == "success") {
                        Timber.i("Login successful for user: ${authResponse.data.user.email}")
                        sessionManager.saveAuthData(authResponse.data)
                        emit(ResourceHelper.Success(authResponse))
                    } else {
                        Timber.w("Login failed with status: ${authResponse.status}, message: ${authResponse.message}")
                        emit(ResourceHelper.Error(authResponse.message))
                    }
                } ?: run {
                    Timber.e("Login response body is null")
                    emit(ResourceHelper.Error("Response body is null"))
                }
            } else {
                Timber.e("Login failed with code: ${response.code()}, message: ${response.message()}")
                emit(ResourceHelper.Error("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Login error occurred")
            emit(ResourceHelper.Error(e.message ?: "An error occurred during login"))
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ResourceHelper<AuthResponse>> = flow {
        emit(ResourceHelper.Loading())
        try {
            Timber.d("Attempting registration for email: $email")
            val response = apiService.register(RegisterRequest(name, email, password))
            if(response.isSuccessful) {
                response.body()?.let { authResponse ->
                    if (authResponse.status == "success") {
                        Timber.i("Registration successful for user: ${authResponse.data.user.email}")
                        sessionManager.saveAuthData(authResponse.data)
                        emit(ResourceHelper.Success(authResponse))
                    } else {
                        Timber.w("Registration failed with status: ${authResponse.status}")
                        emit(ResourceHelper.Error(authResponse.message))
                    }
                } ?: run {
                    Timber.e("Registration response body is null")
                    emit(ResourceHelper.Error("Response body is null"))
                }
            } else {
                Timber.e("Registration failed with code: ${response.code()}")
                emit(ResourceHelper.Error("Register failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Registration error occurred")
            emit(ResourceHelper.Error(e.message ?: "An error occurred during Register"))
        }
    }

}