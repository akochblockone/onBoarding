package com.example.akochb1onboarding

import android.app.Application
import com.example.akochb1onboarding.db.DataBaseProvider
import com.example.akochb1onboarding.di.appModule
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        DataBaseProvider.init(this)
        super.onCreate()
        startKoin {
            androidContext (this@BaseApplication)
            modules(appModule)
        }
    }

    override fun onTerminate() {
        DataBaseProvider.getDataBase().close()
        super.onTerminate()
    }
}