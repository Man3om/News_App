package com.example.newsapp.ui.mainActivity

import androidx.lifecycle.ViewModel
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.ui.repository.NewsRepo
import com.example.newsapp.ui.screens.News.NewsViewModel
import com.example.newsapp.ui.screens.Resources
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivityViewModel : ViewModel() {
    val NewsViewModel = NewsViewModel()
    private var _searchResults =
        MutableStateFlow<Resources<List<ArticlesItem>>>(Resources.Initial())

    val searchResults = _searchResults

    fun getNewsFromSearch(query: String){
        NewsViewModel.getNewsBySourceId(query)
        _searchResults.value = NewsViewModel.articlesResource.value
    }
}