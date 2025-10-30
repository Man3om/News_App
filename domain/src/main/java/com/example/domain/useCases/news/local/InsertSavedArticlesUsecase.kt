package com.example.domain.useCases.news.local

import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.repository.news.NewsRepositoryLocalDataSource

class InsertSavedArticlesUsecase(private val repositoryLocal: NewsRepositoryLocalDataSource) {

    suspend fun execute(data: List<ArticlesItemEntity>, sourceId: String) =
        repositoryLocal.insertSavedArticles(data,sourceId)
}