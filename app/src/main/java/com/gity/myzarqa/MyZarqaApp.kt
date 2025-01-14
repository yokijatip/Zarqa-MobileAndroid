package com.gity.myzarqa

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyZarqaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        ChuckerInterceptor.Builder(this)
    }
}