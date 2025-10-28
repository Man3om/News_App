package com.example.domain.useCases.news.remote

import com.example.domain.reposatory.news.NewsRepositoryRemoteDataSource

class GetNewsBySourceId(private val repository: NewsRepositoryRemoteDataSource ) {
    suspend fun execute(sourceId : String) = repository.getNewsBySourceId(sourceId)
}