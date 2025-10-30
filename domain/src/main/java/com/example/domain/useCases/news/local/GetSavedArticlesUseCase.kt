package com.example.domain.useCases.news.local

import com.example.domain.repository.news.NewsRepositoryLocalDataSource

class GetSavedArticlesUseCase(private val repositoryLocal: NewsRepositoryLocalDataSource) {
    suspend fun execute(sourceId : String) = repositoryLocal.getSavedArticles(sourceId)
}