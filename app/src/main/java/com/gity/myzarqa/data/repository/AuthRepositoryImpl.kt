package com.gity.myzarqa.data.repository

import com.gity.myzarqa.data.remote.api.ApiService
import com.gity.myzarqa.data.remote.dto.AuthResponse
import com.gity.myzarqa.data.remote.dto.AuthResponseCheckEmail
import com.gity.myzarqa.data.remote.dto.AuthResponseVerifyOTP
import com.gity.myzarqa.data.remote.dto.CheckEmailRequest
import com.gity.myzarqa.data.remote.dto.LoginRequest
import com.gity.myzarqa.data.remote.dto.NewPasswordRequest
import com.gity.myzarqa.data.remote.dto.OTPRequest
import com.gity.myzarqa.data.remote.dto.RegisterRequest
import com.gity.myzarqa.domain.repository.AuthRepository
import com.gity.myzarqa.helper.ResourceHelper
import com.gity.myzarqa.helper.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService /* Di Injeksi dari NetworkModule */,
    private val sessionManager: SessionManager
) : AuthRepository {
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
            if (response.isSuccessful) {
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

    override suspend fun checkEmail(email: String): Flow<ResourceHelper<AuthResponseCheckEmail>> =
        flow {
            emit(ResourceHelper.Loading())
            try {
                Timber.d("Attempting check email for email: $email")
                val response = apiService.forgotPassword(CheckEmailRequest(email))
                if (response.isSuccessful) {
                    response.body()?.let { authResponse ->
                        if (authResponse.status == "success") {
                            Timber.i("Check email successful for user: ${authResponse.data.email}")
                            emit(ResourceHelper.Success(authResponse))
                        } else {
                            Timber.w("Check email failed with status: ${authResponse.status}")
                            emit(ResourceHelper.Error(authResponse.message))
                        }
                    } ?: run {
                        Timber.e("Check email response body is null")
                        emit(ResourceHelper.Error("Response body is null"))
                    }
                } else {
                    Timber.e("Check email failed with code: ${response.code()}")
                    emit(ResourceHelper.Error("Check email failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                Timber.e(e, "Check email error occurred")
                emit(ResourceHelper.Error(e.message ?: "An error occurred during check email"))
            }
        }

    override suspend fun checkOTP(otp: String): Flow<ResourceHelper<AuthResponseVerifyOTP>> = flow {
        emit(ResourceHelper.Loading())
        try {
            Timber.d("Attempting Verify OTP for otp: $otp")
            val response = apiService.verifyOTP(OTPRequest(otp))
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    if (authResponse.status == "success") {
                        Timber.i("Verify OTP successful for user: ${authResponse.data.tokenTemporary}")
                        emit(ResourceHelper.Success(authResponse))
                    } else {
                        Timber.w("Verify OTP failed with status: ${authResponse.status}")
                        emit(ResourceHelper.Error(authResponse.message))
                    }
                } ?: run {
                    Timber.e("Verify OTP response body is null")
                    emit(ResourceHelper.Error("Response body is null"))
                }
            } else {
                Timber.e("Verify OTP failed with code: ${response.code()}")
                emit(ResourceHelper.Error("Verify OTP failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Verify OTP error occurred")
            emit(ResourceHelper.Error(e.message ?: "An error occurred during Verify OTP"))
        }
    }

    override suspend fun newPassword(tokenTemporary: String, newPassword: String): Flow<ResourceHelper<AuthResponse>> =
        flow {
            emit(ResourceHelper.Loading())
            try {
                Timber.d("Attempting New Password for newPassword: $newPassword")
                val response = apiService.newPassword(NewPasswordRequest(tokenTemporary, newPassword))
                if (response.isSuccessful) {
                    response.body()?.let { authResponse ->
                        if (authResponse.status == "success") {
                            Timber.i("New Password successful for user: ${authResponse.data.user.email}")
                            emit(ResourceHelper.Success(authResponse))
                        } else {
                            Timber.w("New Password failed with status: ${authResponse.status}")
                            emit(ResourceHelper.Error(authResponse.message))
                        }
                    } ?: run {
                        Timber.e("New Password response body is null")
                        emit(ResourceHelper.Error("Response body is null"))
                    }
                } else {
                    Timber.e("New Password failed with code: ${response.code()}")
                    emit(ResourceHelper.Error("New Password failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                Timber.e(e, "New Password error occurred")
                emit(ResourceHelper.Error(e.message ?: "An error occurred during New Password"))
            }
        }

}