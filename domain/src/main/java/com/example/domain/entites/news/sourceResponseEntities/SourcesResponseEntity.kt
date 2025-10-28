package com.example.domain.entites.news.sourceResponseEntities



data class SourcesResponseEntity(
	val sources: List<SourcesItemEntity>? = null,

	val message: String? = null,

	val status: String? = null
)