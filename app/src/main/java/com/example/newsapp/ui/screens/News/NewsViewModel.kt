package com.example.newsapp.ui.screens.News

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.entites.news.sourceResponseEntities.SourcesItemEntity
import com.example.domain.repository.news.NewsRepository
import com.example.domain.useCases.news.GetNewsBySourceIdUsecase
import com.example.domain.useCases.news.GetSourcesUsecase
import com.example.domain.utils.base.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    val selectedSourceId = MutableStateFlow<String>("")
    val selectedItemIndex = MutableStateFlow<Int>(-1)
    val sourcesResource = MutableStateFlow<Resources<List<SourcesItemEntity>>>(Resources.Initial())
    val articlesResource = MutableStateFlow<Resources<List<ArticlesItemEntity>>>(Resources.Initial())

    val newsRepository : NewsRepository =
    val getSourcesUseCase = GetSourcesUsecase()
    val getNewsBySourceIdUseCase = GetNewsBySourceIdUsecase()

    fun getSources(categoryApiId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sourcesResource.value = Resources.Loading()
            sourcesResource.value = getSourcesUseCase.execute(categoryApiId)
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        selectedSourceId.value = sourceId
        viewModelScope.launch(Dispatchers.IO) {
            articlesResource.value = Resources.Loading()
            articlesResource.value = getNewsBySourceIdUseCase.execute(sourceId = sourceId)
        }
    }

}