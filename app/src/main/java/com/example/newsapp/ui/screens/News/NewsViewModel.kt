package com.example.newsapp.ui.screens.News

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.everythingResponseApiModel.ArticlesItem
import com.example.newsapp.api.model.everythingResponseApiModel.EverythingResponse
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesItem
import com.example.newsapp.api.model.sourceResponseApiModel.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel()  {

    val sourcesList = mutableStateListOf<SourcesItem>()
    val articlesList = mutableStateListOf<ArticlesItem>()

    fun getSources(categoryApiId: String) {
        val TAG = "Sources API"

        ApiManager.webServices().getNewsSources(categoryApiId = categoryApiId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse?>, response: Response<SourcesResponse?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.i(TAG, "onResponse: ${responseBody?.status}")
                        Log.i(TAG, "onResponse: ${responseBody?.sources}")
                        sourcesList.clear()
                        sourcesList.addAll(responseBody?.sources?: listOf())

                    } else {
                        val errorBody = response.errorBody()
                        Log.e(TAG, "onResponse: $errorBody")
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun getNewsBySourceId(sourceId: String) {
        ApiManager.webServices().getNewsBySource(sources = sourceId)
            .enqueue(object : Callback<EverythingResponse> {
                override fun onResponse(
                    call: Call<EverythingResponse?>, response: Response<EverythingResponse?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.i("News API", "onResponse: ${responseBody?.status}")
                        Log.i("News API", "onResponse: ${responseBody?.articles}")
                        articlesList.clear()
                        articlesList.addAll(responseBody?.articles?: listOf())
                    } else {
                        val errorBody = response.errorBody()
                        Log.e("News API", "onResponse: $errorBody")
                    }
                }

                override fun onFailure(
                    call: Call<EverythingResponse?>, t: Throwable
                ) {
                    Log.e("News API", "onFailure: ${t.message}")
                }

            })

    }

}