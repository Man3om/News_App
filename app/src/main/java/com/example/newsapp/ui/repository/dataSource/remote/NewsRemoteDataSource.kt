package com.example.newsapp.ui.repository.dataSource.remote

import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItemDM
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesResponse
import com.example.newsapp.ui.screens.Resources
import com.google.gson.Gson

class NewsRemoteDataSource {
    suspend fun searchArticles(query: String): Resources<List<ArticlesItem>> {
        return try {
            val response =
                ApiManager.webServices().getNewsSearch(search = query)

            if (response.isSuccessful) {
                Resources.Success(response.body()?.articles ?: listOf())
            } else {
                val error = response.errorBody()?.string()
                val gson = Gson()
                val errorResponse = gson.fromJson(error, SourcesResponse::class.java)
                Resources.Error(errorResponse.message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            Resources.Error(e.message ?: "Something went wrong")
        }
    }

    suspend fun getSources(category: String): Resources<List<SourcesItemDM>> {
        return try {
            val response =
                ApiManager.webServices().getNewsSources(categoryApiId = category)

            if (response.isSuccessful) {
                Resources.Success(response.body()?.sources ?: listOf())
            } else {
                val error = response.errorBody()?.string()
                val gson = Gson()
                val errorResponse = gson.fromJson(error, SourcesResponse::class.java)
                Resources.Error(errorResponse.message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            Resources.Error(e.message ?: "Something went wrong")
        }
    }

    suspend fun getNewsBySourceId(sourceId: String): Resources<List<ArticlesItem>> {
        return try {
            val response = ApiManager.webServices().getNewsBySource(sources = sourceId, 1, 15)

            if (response.isSuccessful) {
                Resources.Success(response.body()?.articles ?: listOf())
            } else {
                val gson = Gson()
                val errorResponse = gson.fromJson(
                    response.errorBody()?.string(),
                    EverythingResponse::class.java
                )

                Resources.Error(errorResponse.message ?: "Something went wrong")
            }
        } catch (e: Exception) {
            Resources.Error(e.message ?: "Something went wrong")
        }
    }
}

