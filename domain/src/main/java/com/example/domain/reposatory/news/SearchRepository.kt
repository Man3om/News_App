package com.example.domain.reposatory.news

import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.utils.base.Resources

interface SearchRepository {
    suspend fun searchArticles(query: String): Resources<List<ArticlesItemEntity>>
}


interface SearchRepositoryRemoteDataSource
{
    suspend fun searchArticles(query: String): Resources<List<ArticlesItemEntity>>
}