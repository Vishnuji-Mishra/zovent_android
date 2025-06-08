package com.app.zovent.utils

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
//import com.orhanobut.hawk.Hawk

class MyApplication : Application() {
    lateinit var instance: MyApplication
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        instance = this
//        Hawk.init(this).build()

    }
}