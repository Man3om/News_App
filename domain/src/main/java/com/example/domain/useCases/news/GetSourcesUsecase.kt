package com.example.domain.useCases.news

import com.example.domain.repository.news.NewsRepository
import javax.inject.Inject

class GetSourcesUsecase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(category: String) = repository.getSources(category)

}