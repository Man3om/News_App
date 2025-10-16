package com.example.newsapp.ui.destinations

import com.example.newsapp.model.CategoryItemDM
import kotlinx.serialization.Serializable

@Serializable
data object CategoriesDestinations

@Serializable
data class NewsDestinations(val categoryApiId: String)