package com.example.newsapp.ui.screens.News

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.entites.news.sourceResponseEntities.SourcesItemEntity
import com.example.domain.useCases.news.GetNewsBySourceIdUsecase
import com.example.domain.useCases.news.GetSourcesUsecase
import com.example.domain.utils.base.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor( private val getSourcesUseCase : GetSourcesUsecase ,
   private val getNewsBySourceIdUseCase : GetNewsBySourceIdUsecase) : ViewModel() {
    val selectedSourceId = MutableStateFlow<String>("")
    val selectedItemIndex = MutableStateFlow<Int>(-1)
    val sourcesResource = MutableStateFlow<Resources<List<SourcesItemEntity>>>(Resources.Initial())
    val articlesResource = MutableStateFlow<Resources<List<ArticlesItemEntity>>>(Resources.Initial())

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