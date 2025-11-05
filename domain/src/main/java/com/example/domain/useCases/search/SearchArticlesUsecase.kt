package com.example.domain.useCases.search

import com.example.domain.repository.search.SearchRepository
import javax.inject.Inject

class SearchArticlesUsecase @Inject constructor(private val repository: SearchRepository) {

    suspend fun execute(query : String) = repository.searchArticles(query)
}