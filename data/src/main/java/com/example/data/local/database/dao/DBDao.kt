package com.example.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.models.news.everythingResponseApiModel.ArticlesItemModel
import com.example.data.models.news.sourceResponseApiModel.SourcesItemModel

@Dao
interface DBDao {

    // Sources Table
    @Insert(onConflict = OnConflictStrategy.REPLACE , entity = SourcesItemModel::class)
    suspend fun insertSources(sources: List<SourcesItemModel>)
    @Query("select * from sources where category = :category")
    suspend fun getSources(category : String):List<SourcesItemModel>

    // Source_Articles Table
    @Insert(onConflict = OnConflictStrategy.REPLACE , entity = ArticlesItemModel::class)
    suspend fun insertArticles(articles: List<ArticlesItemModel>)
    @Query("select * from articles where sourceId = :sourceId")
    suspend fun getArticles(sourceId:String): List<ArticlesItemModel>
}