package com.example.domain.reposatory.news

import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.entites.news.sourceResponseEntities.SourcesItemEntity
import com.example.domain.utils.base.Resources

interface NewsRepository {
    suspend fun getSources(category: String): Resources<List<SourcesItemEntity>>
    suspend fun getNewsBySourceId(sourceId: String): Resources<List<ArticlesItemEntity>>
}

interface NewsRepositoryLocalDataSource {
    suspend fun getSavedSources(category: String): Resources<List<SourcesItemEntity>>
    suspend fun insertSavedSources(sources: List<SourcesItemEntity>): Resources<Unit>
    suspend fun getSavedArticles(sourceId: String): Resources<List<ArticlesItemEntity>>
    suspend fun insertSavedArticles(
        data: List<ArticlesItemEntity>,
        sourceId: String
    ): Resources<Unit>

}


interface NewsRepositoryRemoteDataSource {
    suspend fun getSources(category: String): Resources<List<SourcesItemEntity>>
    suspend fun getNewsBySourceId(sourceId: String): Resources<List<ArticlesItemEntity>>
}