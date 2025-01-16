package com.gity.myzarqa.presentation.common.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gity.myzarqa.databinding.ActivitySplashBinding
import com.gity.myzarqa.presentation.auth.AuthActivity
import com.gity.myzarqa.presentation.common.base.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Suppress("DEPRECATION")
@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Gunakan Timber untuk logging
        Timber.d("SplashActivity Created")
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            // Delay 2 detik untuk menampilkan splash screen
            delay(2000)

            if (viewModel.checkLoginStatus()) {
                Timber.d("User is logged in, navigating to MainActivity")
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                Timber.d("User is not logged in, navigating to AuthActivity")
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
            }
            finish()
        }
    }
}