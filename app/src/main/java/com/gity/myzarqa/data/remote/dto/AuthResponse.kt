package com.gity.myzarqa.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: AuthData,
    @SerializedName("message")
    val message: String
)

data class AuthData(
    @SerializedName("user")
    val user: UserData,
    @SerializedName("token")
    val token: String
)

// Model data untuk Domain Layer
data class UserData(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val name: String,
    @SerializedName("name")
    val email: String,
    @SerializedName("role")
    val role: String
)

data class AuthResponseCheckEmail(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: AuthDataCheckEmail,
    @SerializedName("message")
    val message: String
)

data class AuthDataCheckEmail(
    @SerializedName("email")
    val email: String
)

data class AuthResponseVerifyOTP(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: AuthDataVerifyOTP,
    @SerializedName("message")
    val message: String
)

data class AuthDataVerifyOTP(
    @SerializedName("token")
    val tokenTemporary: String
)

