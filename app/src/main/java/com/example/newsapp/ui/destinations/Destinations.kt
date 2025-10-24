package com.example.newsapp.ui.destinations

import kotlinx.serialization.Serializable

@Serializable
data object CategoriesDestinations

@Serializable
data class NewsDestinations(val categoryApiId: String)

@Serializable
data object SearchDestination

@Serializable
data class ArticleDestinations(val description: String, val imageUrl: String, val articleUrl: String)