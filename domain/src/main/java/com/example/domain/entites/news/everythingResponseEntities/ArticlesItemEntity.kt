package com.example.domain.entites.news.everythingResponseEntities

data class ArticlesItemEntity(
    var sourceId : String,

	val publishedAt: String? = null,

	val author: String? = null,

	val urlToImage: String? = null,

	val description: String? = null,

	val title: String? = null,

	val url: String? = null,

	val content: String? = null
)