package com.example.newsapp.ui

import android.app.Application
import com.example.newsapp.api.database.init.AppDatabase

class NewsApplication : Application() {
    companion object {
        lateinit var instance: NewsApplication
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDatabase.Companion.buildDatabase(this)
    }
}