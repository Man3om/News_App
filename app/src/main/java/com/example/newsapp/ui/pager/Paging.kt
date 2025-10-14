package com.example.newsapp.ui.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem

class PagingSource(val sourceId: String) :
    PagingSource<Int, ArticlesItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1

            val response = ApiManager.webServices().getNewsBySource(sourceId, nextPageNumber)
            val articles = response.body()?.articles ?: listOf()

            LoadResult.Page(
                data = articles,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(Throwable(message = e.message))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}