package com.example.domain.entites.news.sourceResponseEntities


data class SourcesItemEntity(
	val country: String? = null,

	val name: String? = null,

	val description: String? = null,

	val language: String? = null,

	var id: String,

	val category: String? = null,

	val url: String? = null
)