package com.example.domain.useCases.news.local

import com.example.domain.reposatory.news.NewsRepositoryLocalDataSource

class GetSavedSourcesUsecase(private val repositoryLocal: NewsRepositoryLocalDataSource) {

    suspend fun execute(category: String) = repositoryLocal.getSavedSources(category)
}