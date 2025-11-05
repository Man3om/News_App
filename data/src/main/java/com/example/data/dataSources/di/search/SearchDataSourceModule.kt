package com.example.data.dataSources.di.search

import com.example.data.dataSources.search.remote.SearchRemoteDataSourceImpl
import com.example.domain.repository.search.SearchRepositoryRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsSearchRemoteDataSource(implements: SearchRemoteDataSourceImpl): SearchRepositoryRemoteDataSource
}