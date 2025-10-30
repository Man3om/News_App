package com.example.data.mapper

import com.example.data.models.news.everythingResponseApiModel.ArticlesItemModel
import com.example.data.models.news.sourceResponseApiModel.SourcesItemModel
import com.example.domain.entites.news.everythingResponseEntities.ArticlesItemEntity
import com.example.domain.entites.news.sourceResponseEntities.SourcesItemEntity

fun SourcesItemModel.toEntity() =
    SourcesItemEntity(country, name, description, language, id, category, url)

fun ArticlesItemModel.toEntity() =
    ArticlesItemEntity(sourceId, publishedAt, author, urlToImage, description, title, url, content)

fun SourcesItemEntity.toModel() = SourcesItemModel(country, name, description, language, id, category, url)

fun ArticlesItemEntity.toModel() = ArticlesItemModel(
    sourceId = sourceId,
    publishedAt = publishedAt,
    author = author,
    urlToImage = urlToImage,
    description = description,
    title = title,
    url = url,
    content = content,
)