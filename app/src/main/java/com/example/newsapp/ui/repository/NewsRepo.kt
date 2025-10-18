package com.example.newsapp.ui.repository


import android.util.Log
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItemDM
import com.example.newsapp.ui.repository.dataSource.local.NewsLocalDataSource
import com.example.newsapp.ui.repository.dataSource.remote.NewsRemoteDataSource
import com.example.newsapp.ui.screens.News.Resources
import com.example.newsapp.utils.ApplicationUtils.isNetworkAvailable

class NewsRepo {
    val newsLocalDataSource = NewsLocalDataSource()
    val newsRemoteDataSource = NewsRemoteDataSource()

    suspend fun getSources(category: String): Resources<List<SourcesItemDM>> {
        val isConnected = isNetworkAvailable()

        return if (isConnected) {
            val state = newsRemoteDataSource.getSources(category)
            if (state is Resources.Success) {
                newsLocalDataSource.insertSavedSources(state.response)
            }

            state
        } else {
            newsLocalDataSource.getSavedSources(category)
        }
    }

    suspend fun getNewsBySourceId(sourceId: String): Resources<List<ArticlesItem>> {
        val isConnected = isNetworkAvailable()

        return if (isConnected) {
            val state = newsRemoteDataSource.getNewsBySourceId(sourceId)
            if (state is Resources.Success) {
                Log.d("GetNewsBySourceId", "getNewsBySourceId: ${state.response}")
                newsLocalDataSource.insertSavedArticles(state.response, sourceId)
            }

            state

        } else {
            newsLocalDataSource
                .getSavedArticles(sourceId)
        }
    }
}