package com.gity.myzarqa.helper

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.gity.myzarqa.data.remote.dto.AuthData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAuthData(authData: AuthData) {
        sharedPreferences.edit()
            .putString("token", authData.token)
            .putString("user_id", authData.user.id)
            .putString("user_name", authData.user.name)
            .putString("user_email", authData.user.email)
            .putString("user_role", authData.user.role)
            .apply()
    }

    fun getToken(): String? = sharedPreferences.getString("token", null)

    fun isLoggedIn(): Boolean = getToken() != null

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}