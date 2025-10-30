package com.example.domain.useCases.news

import com.example.domain.repository.news.NewsRepository

class GetNewsBySourceIdUsecase(private val repository: NewsRepository ) {
    suspend fun execute(sourceId : String) = repository.getNewsBySourceId(sourceId)
}