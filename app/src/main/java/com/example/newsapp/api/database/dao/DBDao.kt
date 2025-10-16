package com.example.newsapp.api.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItemDM

@Dao
interface DBDao {

    // Sources Table
    @Insert(onConflict = OnConflictStrategy.REPLACE , entity = SourcesItemDM::class)
    suspend fun insertSources(sources: List<SourcesItemDM>)
    @Query("select * from sources where category = :category")
    suspend fun getSources(category : String):List<SourcesItemDM>

    // Source_Articles Table
    @Insert(onConflict = OnConflictStrategy.REPLACE , entity = ArticlesItem::class)
    suspend fun insertArticles(articles: List<ArticlesItem>)
    @Query("select * from articles where sourceId = :sourceId")
    suspend fun getArticles(sourceId:String): List<ArticlesItem>
}