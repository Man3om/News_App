package com.example.newsapp.api

import com.example.newsapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getNewsSources() : Call<SourcesResponse>
}