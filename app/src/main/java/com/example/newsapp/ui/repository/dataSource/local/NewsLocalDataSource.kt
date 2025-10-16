package com.example.newsapp.ui.repository.dataSource.local

import com.example.newsapp.api.database.init.AppDatabase
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItemDM
import com.example.newsapp.ui.screens.News.Resources

class NewsLocalDataSource {
    suspend fun getSavedSources(category: String): Resources<List<SourcesItemDM>> {
        try {
            val sources = AppDatabase.getInstance().appDao().getSources(category)

            return if (sources.isNotEmpty()) {
                Resources.Success(sources)
            } else {
                Resources.Error("No Sources Found")
            }
        }
        catch (e : Exception){
            return Resources.Error(e.message ?: "Couldn't get Sources")
        }
    }

    suspend fun insertSavedSources(sources: List<SourcesItemDM>): Resources<Unit> {
        try {
            AppDatabase.getInstance().appDao().insertSources(sources)
            return Resources.Success(Unit)
        }
        catch (e : Exception){
            return Resources.Error(e.message ?: "Couldn't insert Sources")
        }
    }

    suspend fun getSavedArticles(sourceId: String): Resources<List<ArticlesItem>> {
        try {
            val dataArticles = AppDatabase.getInstance().appDao().getArticles(sourceId)
            return Resources.Success(dataArticles)
        }
        catch (e : Exception){
            return Resources.Error(e.message ?: "Couldn't get Sources")
        }
    }

    suspend fun insertSavedArticles(data: List<ArticlesItem>, sourceId: String): Resources<Unit> {
        try {
            data.forEach {
                it.sourceId = sourceId
            }
            AppDatabase.getInstance().appDao().insertArticles(data)
            return Resources.Success(Unit)
        }
        catch (e : Exception){
            return Resources.Error(e.message ?: "Couldn't insert Sources")
        }
    }
}