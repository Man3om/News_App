package com.example.newsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem


class ArticlesPagingSource(val sourceId: String) : PagingSource<Int, ArticlesItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        val page = params.key ?: 1
        return try {
            val response = ApiManager.webServices().getNewsBySource(sources = sourceId, page = page)
            val articles = response.articles ?: listOf()
            LoadResult.Page(
                articles,
                if (page == 1) null else page - 1,
                if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}