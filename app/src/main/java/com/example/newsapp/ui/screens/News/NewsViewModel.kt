package com.example.newsapp.ui.screens.News

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItem
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesResponse
import com.example.newsapp.ui.pager.PagingSource
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.collections.mutableListOf

class NewsViewModel : ViewModel() {
    private var articlesList_ = mutableStateListOf<ArticlesItem>()
    val articlesList = articlesList_
    val sourceError = mutableStateOf("")
    val articlesError = mutableStateOf("")
    val selectedSourceId = MutableStateFlow("")
    private var sourcesList_ = mutableStateListOf<SourcesItem>()
    val sourcesList = sourcesList_

    fun getSources(categoryApiId: String) {
        viewModelScope.launch {
            try {
                val response =
                    ApiManager.webServices().getNewsSources(categoryApiId = categoryApiId)

                if (response.isSuccessful) {
                    sourcesList_.clear()
                    sourcesList_.addAll(response.body()?.sources ?: listOf())
                } else {
                    val error = response.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(error, SourcesResponse::class.java)
                    sourceError.value = errorResponse.message ?: "Something went wrong"
                }
            } catch (e: Exception) {
                sourceError.value = e.message.toString()
                e.printStackTrace()
            }
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        selectedSourceId.value = sourceId
        viewModelScope.launch {
            try {
                val response = ApiManager.webServices().getNewsBySource(sources = sourceId, 1, 15)

                if (response.isSuccessful) {
                    articlesList_.clear()
                    articlesList_.addAll(response.body()?.articles ?: listOf())
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        response.errorBody()?.string(),
                        EverythingResponse::class.java
                    )
                    articlesError.value = errorResponse.message ?: "Something went wrong"
                }
            } catch (e: Exception) {
                articlesError.value = e.message.toString()
                e.printStackTrace()
            }
        }
    }

}