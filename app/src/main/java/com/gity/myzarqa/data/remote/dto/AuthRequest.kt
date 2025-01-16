package com.gity.myzarqa.data.remote.dto

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class CheckEmailRequest(
    val email: String
)

data class OTPRequest(
    val otp: String
)

data class NewPasswordRequest(
    val token: String,
    val newPassword: String
)