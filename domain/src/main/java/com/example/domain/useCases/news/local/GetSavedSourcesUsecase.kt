package com.example.domain.useCases.news.local

import com.example.domain.repository.news.NewsRepositoryLocalDataSource

class GetSavedSourcesUsecase(private val repositoryLocal: NewsRepositoryLocalDataSource) {

    suspend fun execute(category: String) = repositoryLocal.getSavedSources(category)
}