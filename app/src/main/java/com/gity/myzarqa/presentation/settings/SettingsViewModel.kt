package com.gity.myzarqa.presentation.settings

import androidx.lifecycle.ViewModel
import com.gity.myzarqa.helper.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {
    fun logout() {
        sessionManager.clearSession()
    }
}