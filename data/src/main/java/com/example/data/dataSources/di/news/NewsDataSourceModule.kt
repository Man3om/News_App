package com.example.data.dataSources.di.news

import com.example.data.dataSources.news.local.NewsLocalDataSourceImpl
import com.example.data.dataSources.news.remote.NewsRemoteDataSourceImpl
import com.example.domain.repository.news.NewsRepositoryLocalDataSource
import com.example.domain.repository.news.NewsRepositoryRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 abstract class NewsDataSourceModule {
    @Binds
    @Singleton
   abstract fun bindsNewsRemoteDataSource(implement : NewsRemoteDataSourceImpl) : NewsRepositoryRemoteDataSource

    @Binds
    @Singleton
   abstract fun bindsNewsLocalDataSource(implement: NewsLocalDataSourceImpl) : NewsRepositoryLocalDataSource
}