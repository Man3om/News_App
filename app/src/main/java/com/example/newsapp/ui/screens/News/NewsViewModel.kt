package com.example.newsapp.ui.screens.News

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItemDM
import com.example.newsapp.ui.repository.NewsRepo
import com.example.domain.utils.base.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    val selectedSourceId = MutableStateFlow<String>("")
    val selectedItemIndex = MutableStateFlow<Int>(-1)
    val sourcesResource = MutableStateFlow<Resources<List<SourcesItemDM>>>(Resources.Initial())
    val articlesResource = MutableStateFlow<Resources<List<ArticlesItem>>>(Resources.Initial())

    val repository = NewsRepo()


    fun getSources(categoryApiId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sourcesResource.value = Resources.Loading()
            sourcesResource.value = repository.getSources(categoryApiId)
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        selectedSourceId.value = sourceId
        viewModelScope.launch(Dispatchers.IO) {
            articlesResource.value = Resources.Loading()
            articlesResource.value = repository.getNewsBySourceId(sourceId)
        }
    }

}