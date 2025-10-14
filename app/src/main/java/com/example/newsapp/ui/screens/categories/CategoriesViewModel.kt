package com.example.newsapp.ui.screens.categories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.CategoryItemDM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoriesViewModel : ViewModel() {

    private var _categories: MutableStateFlow<List<CategoryItemDM>> = MutableStateFlow(listOf())
    val  categories = _categories

    fun getCategories() {
        _categories = loadCategories()
    }

    private fun loadCategories(): MutableStateFlow<List<CategoryItemDM>> {
        return CategoryItemDM.getCategories()
    }

}