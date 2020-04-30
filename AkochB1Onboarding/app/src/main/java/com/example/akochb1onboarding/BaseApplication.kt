package com.example.akochb1onboarding

import android.app.Application
import com.example.akochb1onboarding.db.DataBaseProvider

class BaseApplication: Application() {

    override fun onCreate() {
        DataBaseProvider.init(this)
        super.onCreate()
    }

    override fun onTerminate() {
        DataBaseProvider.getDataBase().close()
        super.onTerminate()
    }
}