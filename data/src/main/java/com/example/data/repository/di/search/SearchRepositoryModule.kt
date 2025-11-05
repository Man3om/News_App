package com.example.data.repository.di.search

import com.example.data.dataSources.search.remote.SearchRemoteDataSourceImpl
import com.example.data.repository.search.SearchRepositoryImpl
import com.example.domain.repository.search.SearchRepository
import com.example.domain.repository.search.SearchRepositoryRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsSearchRepository(implements: SearchRepositoryImpl): SearchRepository
}