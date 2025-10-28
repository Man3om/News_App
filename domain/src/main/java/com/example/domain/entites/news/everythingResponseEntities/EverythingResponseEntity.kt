package com.example.domain.entites.news.everythingResponseEntities

data class EverythingResponseEntity(
	val totalResults: Int? = null,

	val articles: List<ArticlesItemEntity>? = null,

	val message: String? = null,

	val status: String? = null
)