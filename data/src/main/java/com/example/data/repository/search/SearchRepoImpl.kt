package com.example.data.repository.search

import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.repository.search.SearchRepository
import com.example.domain.repository.search.SearchRepositoryRemoteDataSource
import com.example.domain.utils.base.Resources

class SearchRepoImpl(private val searchRemoteDataSource : SearchRepositoryRemoteDataSource ): SearchRepository {
    override suspend fun searchArticles(query: String): Resources<List<ArticlesItemEntity>> {
            val state = searchRemoteDataSource.searchArticles(query)
            return state

    }
}