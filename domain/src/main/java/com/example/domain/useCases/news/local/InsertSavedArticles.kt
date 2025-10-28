package com.example.domain.useCases.news.local

import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.reposatory.news.NewsRepositoryLocalDataSource

class InsertSavedArticles(private val repositoryLocal: NewsRepositoryLocalDataSource) {

    suspend fun execute(data: List<ArticlesItemEntity>, sourceId: String) =
        repositoryLocal.insertSavedArticles(data,sourceId)
}