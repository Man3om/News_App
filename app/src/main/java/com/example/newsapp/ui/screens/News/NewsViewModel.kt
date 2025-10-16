package com.example.newsapp.ui.screens.News

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItemDM
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesResponse
import com.example.newsapp.ui.repository.NewsRepo
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    val selectedSourceId = MutableStateFlow<String>("")
    val sourcesResource = MutableStateFlow<Resources<List<SourcesItemDM>>>(Resources.Initial())
    val articlesResource = MutableStateFlow<Resources<List<ArticlesItem>>>(Resources.Initial())

    val repositry = NewsRepo()


    fun getSources(categoryApiId: String) {
        viewModelScope.launch {
            sourcesResource.value = Resources.Loading()
            sourcesResource.value = repositry.getSources(categoryApiId)
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        selectedSourceId.value = sourceId
        viewModelScope.launch {
            articlesResource.value = Resources.Loading()
            articlesResource.value = repositry.getNewsBySourceId(sourceId)
        }
    }

}