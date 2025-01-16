package com.gity.myzarqa.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gity.myzarqa.data.remote.dto.AuthResponse
import com.gity.myzarqa.data.remote.dto.AuthResponseCheckEmail
import com.gity.myzarqa.data.remote.dto.AuthResponseVerifyOTP
import com.gity.myzarqa.domain.repository.AuthRepository
import com.gity.myzarqa.helper.ResourceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _loginState = MutableStateFlow<ResourceHelper<AuthResponse>?>(null)
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<ResourceHelper<AuthResponse>?>(null)
    val registerState = _registerState.asStateFlow()

    private val _checkEmailState = MutableStateFlow<ResourceHelper<AuthResponseCheckEmail>?>(null)
    val checkEmailState = _checkEmailState.asStateFlow()

    private val _checkOTPState = MutableStateFlow<ResourceHelper<AuthResponseVerifyOTP>?>(null)
    val checkOTPState = _checkOTPState.asStateFlow()

    private val _newPasswordState = MutableStateFlow<ResourceHelper<AuthResponse>?>(null)
    val newPasswordState = _newPasswordState.asStateFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        // Set loading ketika mulai proses
        _loginState.value = ResourceHelper.Loading()
        authRepository.login(email, password).collect { result ->
            _loginState.value = result
            Timber.d("Login state: $result")
        }
    }

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        // Set loading ketika mulai proses
        _registerState.value = ResourceHelper.Loading()
        authRepository.register(name, email, password).collect { result ->
            _registerState.value = result
            Timber.d("Register state: $result")
        }
    }

    fun checkEmail(email: String) = viewModelScope.launch {
        // Set loading ketika mulai proses
        _checkEmailState.value = ResourceHelper.Loading()
        authRepository.checkEmail(email).collect { result ->
            _checkEmailState.value = result
            Timber.d("Check Email state: $result")
        }
    }

    fun checkOTP(otp: String) = viewModelScope.launch {
        // Set loading ketika mulai proses
        _checkOTPState.value = ResourceHelper.Loading()
        authRepository.checkOTP(otp).collect { result ->
            _checkOTPState.value = result
            Timber.d("Check OTP state: $result")
        }
    }

    fun newPassword(tokenTemporary: String, newPassword: String) = viewModelScope.launch {
        // Set loading ketika mulai proses
        _newPasswordState.value = ResourceHelper.Loading()
        authRepository.newPassword(tokenTemporary, newPassword).collect {
            _newPasswordState.value = it
            Timber.d("New Password state: $it")
        }
    }

}