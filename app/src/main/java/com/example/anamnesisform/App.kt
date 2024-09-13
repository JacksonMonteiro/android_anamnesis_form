package com.example.anamnesisform

import android.app.Application
import com.example.anamnesisform.data.local.database.AppDatabase
import com.example.anamnesisform.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupDatabase()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(applicationModule)
        }
    }

    private fun setupDatabase() {
        AppDatabase.getDatabase(this)
    }
}