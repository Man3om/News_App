package com.example.data.dataSources.search.remote

import com.example.data.models.news.sourceResponseApiModel.SourcesResponseModel
import com.example.data.remote.api.ApiManager
import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.reposatory.news.SearchRepositoryRemoteDataSource
import com.example.domain.utils.base.Resources
import com.google.gson.Gson

class SearchRemoteDataSourceImpl : SearchRepositoryRemoteDataSource {
    override suspend fun searchArticles(query: String): Resources<List<ArticlesItemEntity>> {
        return try {
            val response =
                ApiManager.webServices().getNewsSearch(search = query)

            if (response.isSuccessful) {
                Resources.Success(response.body()?.articles ?: listOf())
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
}