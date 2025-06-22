package com.app.zovent.utils

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
//import com.orhanobut.hawk.Hawk

class MyApplication : Application() {
    companion object {
        var appContext: Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        appContext = this
//        Hawk.init(this).build()

    }
}