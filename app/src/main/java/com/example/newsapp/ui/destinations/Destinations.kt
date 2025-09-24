package com.example.newsapp.ui.destinations

import com.example.newsapp.model.CategoryItemDM
import kotlinx.serialization.Serializable

@Serializable
object CategoriesDestinations

@Serializable
class NewsDestinations(val category: CategoryItemDM)