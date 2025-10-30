package com.example.domain.useCases.news.remote

import com.example.domain.repository.news.NewsRepositoryRemoteDataSource

class GetSourcesUsecase(private val repository: NewsRepositoryRemoteDataSource) {

    suspend fun execute(category: String) = repository.getSources(category)

}