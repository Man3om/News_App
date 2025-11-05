package com.example.data.di.room

import android.content.Context
import androidx.room.Room
import com.example.data.local.database.init.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context : Context) : AppDatabase {
        val DATABASE_NAME = "app_db"

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

}