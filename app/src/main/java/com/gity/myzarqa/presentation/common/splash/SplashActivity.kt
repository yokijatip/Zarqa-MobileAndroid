package com.gity.myzarqa.presentation.common.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.gity.myzarqa.R
import com.gity.myzarqa.databinding.ActivitySplashBinding
import com.gity.myzarqa.presentation.auth.AuthActivity
import com.gity.myzarqa.presentation.common.base.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
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

    private fun setupUI(){
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}