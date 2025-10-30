package com.example.domain.useCases.search.remote

import com.example.domain.repository.search.SearchRepositoryRemoteDataSource

class SearchArticlesUsecase(private val repository: SearchRepositoryRemoteDataSource) {

    suspend fun execute(query : String) = repository.searchArticles(query)
}