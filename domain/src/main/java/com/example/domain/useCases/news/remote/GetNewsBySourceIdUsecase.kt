package com.example.domain.useCases.news.remote

import com.example.domain.repository.news.NewsRepositoryRemoteDataSource

class GetNewsBySourceIdUsecase(private val repository: NewsRepositoryRemoteDataSource ) {
    suspend fun execute(sourceId : String) = repository.getNewsBySourceId(sourceId)
}