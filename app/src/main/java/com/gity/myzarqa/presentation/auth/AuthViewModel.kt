package com.gity.myzarqa.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gity.myzarqa.data.remote.dto.AuthResponse
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

}