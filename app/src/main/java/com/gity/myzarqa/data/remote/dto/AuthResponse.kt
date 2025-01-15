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