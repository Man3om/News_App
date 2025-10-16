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
    val selectedSourceId = MutableStateFlow<String>("")
    val sourcesResource = MutableStateFlow<Resources<List<SourcesItem>>>(Resources.Initial())
    val articlesResource = MutableStateFlow<Resources<List<ArticlesItem>>>(Resources.Initial())


    fun getSources(categoryApiId: String) {
        viewModelScope.launch {
            try {
                sourcesResource.value = Resources.Loading()
                val response =
                    ApiManager.webServices().getNewsSources(categoryApiId = categoryApiId)

                if (response.isSuccessful) {
                    sourcesResource.value = Resources.Success(response.body()?.sources?: listOf())
                } else {

                    val error = response.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(error, SourcesResponse::class.java)
                    sourcesResource.value = Resources.Error(errorResponse.message?:"Something went wrong")
                }
            } catch (e: Exception) {
                sourcesResource.value = Resources.Error(e.message?:"Something went wrong")
                e.printStackTrace()
            }
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        selectedSourceId.value = sourceId
        viewModelScope.launch {
            try {
                articlesResource.value = Resources.Loading()
                val response = ApiManager.webServices().getNewsBySource(sources = sourceId, 1, 15)

                if (response.isSuccessful) {
                 articlesResource.value = Resources.Success(response.body()?.articles?: listOf())
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        response.errorBody()?.string(),
                        EverythingResponse::class.java
                    )
                    articlesResource.value = Resources.Error(errorResponse.message?:"Something went wrong")
                }
            } catch (e: Exception) {
                articlesResource.value = Resources.Error(e.message?:"Something went wrong")
                e.printStackTrace()
            }
        }
    }

}