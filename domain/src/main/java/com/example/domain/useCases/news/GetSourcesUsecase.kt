package com.example.domain.useCases.news

import com.example.domain.repository.news.NewsRepository

class GetSourcesUsecase(private val repository: NewsRepository) {

    suspend fun execute(category: String) = repository.getSources(category)

}