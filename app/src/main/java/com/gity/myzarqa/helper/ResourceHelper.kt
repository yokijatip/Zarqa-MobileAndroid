package com.gity.myzarqa.helper

sealed class ResourceHelper<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T) : ResourceHelper<T>(data)
    class Error<T>(message: String, data: T? = null) : ResourceHelper<T>(data, message)
    class Loading<T>(data: T? = null) : ResourceHelper<T>(data)
}