package com.example.newsapp.ui.screens.categories
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.CategoryItemDM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoriesViewModel : ViewModel() {

    private var _categories: MutableStateFlow<List<CategoryItemDM>> = MutableStateFlow(listOf())
    fun getCategories(): MutableStateFlow<List<CategoryItemDM>> {
        return loadCategories()
    }

    private fun loadCategories(): MutableStateFlow<List<CategoryItemDM>> {
        return CategoryItemDM.getCategories()
    }

}