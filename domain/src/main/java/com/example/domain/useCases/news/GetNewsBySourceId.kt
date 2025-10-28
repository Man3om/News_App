package com.example.domain.useCases.news

import com.example.domain.reposatory.news.NewsRepository

class GetNewsBySourceId(private val repository: NewsRepository ) {
    suspend fun execute(sourceId : String) = repository.getNewsBySourceId(sourceId)
}