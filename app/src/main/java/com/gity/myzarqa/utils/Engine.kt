package com.gity.myzarqa.utils

import android.content.Context
import com.gity.myzarqa.R

class Engine(private val context: Context) {
    fun startEngine() {
        println(context.getString(R.string.engine_started))
    }
}