package com.example.data.local.database.init

import androidx.room.Database
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
}