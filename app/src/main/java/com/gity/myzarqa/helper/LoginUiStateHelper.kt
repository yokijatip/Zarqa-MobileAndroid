package com.gity.myzarqa.helper

import com.gity.myzarqa.data.remote.dto.AuthResponse

sealed class LoginUiStateHelper {
    object Idle : LoginUiStateHelper()
    object Loading : LoginUiStateHelper()
    data class Success(val data: AuthResponse) : LoginUiStateHelper()
    data class Error(val message: String) : LoginUiStateHelper()
}