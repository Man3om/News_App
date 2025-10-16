package com.example.newsapp

import android.app.Application
import androidx.room.Database
import com.example.newsapp.api.database.init.AppDatabase

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.buildDatabase(this)
    }
}