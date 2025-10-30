package com.example.data.repository.news

import android.util.Log
import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.entites.news.sourceResponseEntities.SourcesItemEntity
import com.example.domain.repository.news.NewsRepository
import com.example.domain.repository.news.NewsRepositoryLocalDataSource
import com.example.domain.repository.news.NewsRepositoryRemoteDataSource
import com.example.domain.utils.base.Resources

class NewsRepoImpl(private val newsRemoteDataSource : NewsRepositoryRemoteDataSource,
    private  val newsLocalDataSource : NewsRepositoryLocalDataSource) : NewsRepository{

    override suspend fun getSources(category: String): Resources<List<SourcesItemEntity>> {
        val isConnected = true

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

    override suspend fun getNewsBySourceId(sourceId: String): Resources<List<ArticlesItemEntity>> {
        val isConnected = true

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