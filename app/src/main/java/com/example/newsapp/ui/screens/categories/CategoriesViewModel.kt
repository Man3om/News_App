package com.example.newsapp.ui.screens.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.CategoryItemDM

class CategoriesViewModel : ViewModel() {
    private lateinit var categories: LiveData<List<CategoryItemDM>>

    fun getCategories() {
        if (!::categories.isInitialized) {
            categories = loadCategories()
        }
    }

    private fun loadCategories(): LiveData<List<CategoryItemDM>> {
        return CategoryItemDM.getCategories()
    }

}