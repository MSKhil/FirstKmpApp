package com.example.kmpapp

import android.app.Application
import com.example.kmpapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class WorkApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@WorkApplication)
        }
    }
}