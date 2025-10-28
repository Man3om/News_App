package com.example.domain.useCases.news.local

import com.example.domain.entites.news.sourceResponseEntities.SourcesItemEntity
import com.example.domain.reposatory.news.NewsRepositoryLocalDataSource

class InsertSavedSourcesUsecase(private val repositoryLocal: NewsRepositoryLocalDataSource) {
    suspend fun execute(sources : List<SourcesItemEntity>) = repositoryLocal.insertSavedSources(sources)
}