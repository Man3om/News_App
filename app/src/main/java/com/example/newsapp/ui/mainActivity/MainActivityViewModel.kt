package com.example.newsapp.ui.mainActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.useCases.search.SearchArticlesUsecase
import com.example.domain.utils.base.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val searchArticlesUsecase: SearchArticlesUsecase) : ViewModel() {
    private var _searchResults =
        MutableStateFlow<Resources<List<ArticlesItemEntity>>>(Resources.Initial())

    val searchResults = _searchResults

    fun searchArticles(query: String) {
        _searchResults.value = Resources.Loading()
        viewModelScope.launch(context = Dispatchers.IO){
            _searchResults.value = searchArticlesUsecase.execute(query = query)
            Log.d("SearchViewModel", "searchArticles: ${searchResults.value}")
        }
    }
}