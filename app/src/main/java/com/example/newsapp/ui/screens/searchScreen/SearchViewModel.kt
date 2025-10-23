package com.example.newsapp.ui.screens.searchScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.ui.repository.NewsRepo
import com.example.newsapp.ui.screens.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private var _searchResults =
        MutableStateFlow<Resources<List<ArticlesItem>>>(Resources.Initial())

    val searchResults = _searchResults

    val searchRepo = NewsRepo()

    fun searchArticles(query: String) {
        _searchResults.value = Resources.Loading()
        viewModelScope.launch(context = Dispatchers.IO){
            _searchResults.value = searchRepo.searchArticles(query)
            Log.d("SearchViewModel", "searchArticles: ${searchResults.value}")
        }

    }
}