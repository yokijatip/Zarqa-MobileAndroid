package com.gity.myzarqa.presentation.common.splash

import androidx.lifecycle.ViewModel
import com.gity.myzarqa.helper.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val sessionManager: SessionManager): ViewModel() {
    // Menggunakan fungsi isLoggedIn yang sudah ada di SessionManager
    fun checkLoginStatus(): Boolean = sessionManager.isLoggedIn()
}