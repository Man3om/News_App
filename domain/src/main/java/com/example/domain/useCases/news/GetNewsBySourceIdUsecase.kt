package com.example.domain.useCases.news

import com.example.domain.repository.news.NewsRepository
import javax.inject.Inject

class GetNewsBySourceIdUsecase @Inject constructor(private val repository: NewsRepository ) {
    suspend fun execute(sourceId : String) = repository.getNewsBySourceId(sourceId)
}