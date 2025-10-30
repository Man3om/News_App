package com.example.data.dataSources.news.local

import android.util.Log
import com.example.data.local.database.init.AppDatabase
import com.example.data.mapper.toEntity
import com.example.data.mapper.toModel
import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.entites.news.sourceResponseEntities.SourcesItemEntity
import com.example.domain.repository.news.NewsRepositoryLocalDataSource
import com.example.domain.utils.base.Resources

class NewsLocalDataSourceImpl : NewsRepositoryLocalDataSource {
    override suspend fun getSavedSources(category: String): Resources<List<SourcesItemEntity>> {
        try {
            val sources = AppDatabase.getInstance().appDao().getSources(category).map {
               it.toEntity()
            }

            return if (sources.isNotEmpty()) {
                Resources.Success(sources)
            } else {
                Resources.Error("No Sources Found")
            }
        } catch (e: Exception) {
            return Resources.Error(e.message ?: "Couldn't get Sources")
        }
    }

    override suspend fun insertSavedSources(sources: List<SourcesItemEntity>): Resources<Unit> {
        try {
            val sources = sources.map {
                it.toModel()
            }
            AppDatabase.getInstance().appDao().insertSources(sources)
            return Resources.Success(Unit)
        } catch (e: Exception) {
            return Resources.Error(e.message ?: "Couldn't insert Sources")
        }
    }

    override suspend fun getSavedArticles(sourceId: String): Resources<List<ArticlesItemEntity>> {
        try {
            val dataArticles = AppDatabase.getInstance().appDao().getArticles(sourceId).map {
                it.toEntity()
            }
            return Resources.Success(dataArticles)
        } catch (e: Exception) {
            return Resources.Error(e.message ?: "Couldn't get Sources")
        }
    }

    override suspend fun insertSavedArticles(
        data: List<ArticlesItemEntity>,
        sourceId: String
    ): Resources<Unit> {
        try {
            val dataModel = data.map {
                it.toModel()
            }

            dataModel.forEach {
                it.sourceId = sourceId
            }
            Log.d("insertSavedArticles", "insertSavedArticles: $data")
            AppDatabase.getInstance().appDao().insertArticles(dataModel)
            return Resources.Success(Unit)
        } catch (e: Exception) {
            return Resources.Error(e.message ?: "Couldn't insert Sources")
        }
    }
}
