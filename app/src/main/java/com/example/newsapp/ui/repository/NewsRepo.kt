package com.example.newsapp.ui.repository

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItemDM
import com.example.newsapp.ui.NewsApplication
import com.example.newsapp.ui.repository.dataSource.local.NewsLocalDataSource
import com.example.newsapp.ui.repository.dataSource.remote.NewsRemoteDataSource
import com.example.newsapp.ui.screens.News.Resources
import com.example.newsapp.utils.Networking

class NewsRepo {
    val newsLocalDataSource = NewsLocalDataSource()
    val newsRemoteDataSource = NewsRemoteDataSource()

    suspend fun getSources(category: String): Resources<List<SourcesItemDM>> {
        val networkMonitor = Networking(NewsApplication.instance)
        val isConnected = networkMonitor.isConnected.value

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
        val networkMonitor = Networking(NewsApplication.instance)
        val isConnected = networkMonitor.isConnected.value

        return if (isConnected) {
            val state = newsRemoteDataSource.getNewsBySourceId(sourceId)
            if (state is Resources.Success) {
                newsLocalDataSource.insertSavedArticles(state.response, sourceId)
            }

            state

        } else {
            newsLocalDataSource
                .getSavedArticles(sourceId)
        }
    }
}