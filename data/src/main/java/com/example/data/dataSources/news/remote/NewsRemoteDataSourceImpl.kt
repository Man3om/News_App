package com.example.data.dataSources.news.remote

import com.example.data.models.news.everythingResponseApiModel.EverythingResponseModel
import com.example.data.models.news.sourceResponseApiModel.SourcesResponseModel
import com.example.data.remote.api.ApiManager
import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.entites.news.sourceResponseEntities.SourcesItemEntity
import com.example.domain.reposatory.news.NewsRepositoryRemoteDataSource
import com.example.domain.utils.base.Resources
import com.google.gson.Gson

class NewsRemoteDataSourceImpl: NewsRepositoryRemoteDataSource {
    override suspend fun getSources(category: String): Resources<List<SourcesItemEntity>> {
        return try {
            val response =
                ApiManager.webServices().getNewsSources(categoryApiId = category)

            if (response.isSuccessful) {
                Resources.Success(response.body()?.sources ?: listOf())
            } else {
                val error = response.errorBody()?.string()
                val gson = Gson()
                val errorResponse = gson.fromJson(error, SourcesResponseModel::class.java)
                Resources.Error(errorResponse.message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            Resources.Error(e.message ?: "Something went wrong")
        }
    }

    override suspend fun getNewsBySourceId(sourceId: String): Resources<List<ArticlesItemEntity>> {
        return try {
            val response = ApiManager.webServices().getNewsBySource(sources = sourceId)

            if (response.isSuccessful) {
                Resources.Success(response.body()?.articles ?: listOf())
            } else {
                val gson = Gson()
                val errorResponse = gson.fromJson(
                    response.errorBody()?.string(),
                    EverythingResponseModel::class.java
                )

                Resources.Error(errorResponse.message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            Resources.Error(e.message ?: "Something went wrong")
        }
    }
    }
}