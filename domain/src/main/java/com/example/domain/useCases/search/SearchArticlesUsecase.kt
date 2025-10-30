package com.example.domain.useCases.search

import com.example.domain.repository.search.SearchRepository

class SearchArticlesUsecase(private val repository: SearchRepository) {

    suspend fun execute(query : String) = repository.searchArticles(query)
}