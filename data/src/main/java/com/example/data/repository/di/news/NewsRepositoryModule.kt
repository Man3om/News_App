package com.example.data.repository.di.news

import com.example.data.repository.news.NewsRepositoryImpl
import com.example.domain.repository.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsNewsRepository(
        implement: NewsRepositoryImpl
    ): NewsRepository
}