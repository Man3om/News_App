package com.example.data.local.database.init

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.database.dao.DBDao
import com.example.data.models.news.everythingResponseApiModel.ArticlesItemModel
import com.example.data.models.news.sourceResponseApiModel.SourcesItemModel

@Database(
    entities = [SourcesItemModel::class, ArticlesItemModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): DBDao

    companion object {
        private const val DATABASE_NAME = "app_db"

        @Volatile
        private var roomInstance: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return roomInstance!!
        }

        fun buildDatabase(context: Context) {
            if (roomInstance == null) {
                roomInstance=  Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
            }
        }
    }
}